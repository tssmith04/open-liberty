/*******************************************************************************
 * Copyright (c) 2019, 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package io.openliberty.microprofile.reactive.streams.test.utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.hamcrest.Matcher;

/**
 *
 */
public class CompletionStageResult<R> {

    private final CompletableFuture<R> resultFuture;

    public static <R> CompletionStageResult<R> from(CompletionStage<R> stage) {
        CompletableFuture<R> resultFuture = new CompletableFuture<>();
        stage.handle((r, t) -> {
            if (t == null) {
                resultFuture.complete(r);
            } else {
                resultFuture.completeExceptionally(t);
            }
            return null;
        });

        return new CompletionStageResult<>(resultFuture);
    }

    private CompletionStageResult(CompletableFuture<R> resultFuture) {
        this.resultFuture = resultFuture;
    }

    public void assertResult(Matcher<R> matcher) {
        try {
            R result = resultFuture.get(10, TimeUnit.SECONDS);
            assertThat(result, matcher);
        } catch (ExecutionException e) {
            throw new AssertionError("Result was exception: " + e, e);
        } catch (InterruptedException e) {
            fail("Interrupted while waiting for result");
        } catch (TimeoutException e) {
            fail("Timed out waiting for result");
        }
    }

    public void assertException(Matcher<? super Throwable> matcher) {
        try {
            R result = resultFuture.get(10, TimeUnit.SECONDS);
            fail("Result was success: " + result);
        } catch (ExecutionException e) {
            assertThat(e.getCause(), matcher);
        } catch (InterruptedException e) {
            fail("Interrupted while waiting for result");
        } catch (TimeoutException e) {
            fail("Timed out waiting for result");
        }
    }

}
