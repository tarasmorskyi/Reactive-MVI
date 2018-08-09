package com.tarasmorskyi.demoappkotlin.domain.repositories;

import com.tarasmorskyi.demoappkotlin.R;
import com.tarasmorskyi.demoappkotlin.App;
import com.tarasmorskyi.demoappkotlin.utils.errors.AppError;
import com.tarasmorskyi.demoappkotlin.utils.errors.ResponseError;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.functions.Function;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;
import timber.log.Timber;

class RxUtils {

  public static final int OTHER_ERROR_CODE = 999;
  private static final int NOT_AUTHORIZED = 401;
  private static final int UNPROCESSABLE_ENTITY = 422;

  private RxUtils() {
  }

  @android.support.annotation.NonNull static <T> ObservableTransformer<Result<T>, T> transformObservableResult() {
    return response -> response.map(RxUtils::returnResultOrError);
  }

  @android.support.annotation.NonNull static <T> SingleTransformer<Result<T>, T> transformSingleResult() {
    return response -> response.map(RxUtils::returnResultOrError);
  }

  @android.support.annotation.NonNull static <T> MaybeTransformer<Result<T>, T> transformMaybeResult() {
    return response -> response.map(RxUtils::returnResultOrError);
  }

  private static <T> T returnResultOrError(@android.support.annotation.NonNull Result<T> result) throws
      AppError {
    Response<T> response = result.response();
    if (!result.isError()) {
      if (response != null && response.isSuccessful()) {
        return response.body();
      } else {
        if (response != null) {
          return handleServerError(response);
        }
      }
    } else {
      logErrorMessage(result);
      if (response != null) {
        return handleServerError(response);
      }
    }
    throw new ResponseError(OTHER_ERROR_CODE, "network connection problem");
  }

  @SuppressWarnings("ChainOfInstanceofChecks")
  private static <T> void logErrorMessage(@android.support.annotation.NonNull Result<T> result) {
    if (result.error() instanceof SocketTimeoutException) {
      Timber.w(result.error(), "socket timeout");
    } else if (result.error() instanceof IOException) {
      if (result.error() instanceof java.net.ConnectException) {
        Timber.w(result.error(), "connect exception");
      } else if (result.error() instanceof SocketTimeoutException) {
        Timber.w(result.error(), "socket timeout");
      } else {
        Timber.w(result.error(), "other network io exception");
        Response<T> response = result.response();
        if (response != null) {
          Timber.d("body: %s", response.raw().toString());
        }
      }
    } else {
      Timber.w(result.error(), "some other network exception");
    }
  }

  private static <T> T handleServerError(@android.support.annotation.NonNull Response<T> response) throws AppError {
      throw new ResponseError(response.code(), getBodyContent(response));
  }

  private static <T> String getBodyContent(Response<T> response) {
    String bodyContent = App.Companion.getInstance().getResources().getString(R.string.server_error);
    if (response != null) {
      try {
        ResponseBody body = response.errorBody();
        if (body != null) {
          bodyContent = body.toString();
        }
      } catch (Exception e) {
        Timber.d("error parsing error body");
        //ignored
      }
    }
    return bodyContent;
  }


  static Function<Observable<Throwable>, Observable<Long>> exponentialBackoff(int maxRetryCount,
      long delay, TimeUnit unit) {
    return errors -> errors.zipWith(Observable.range(1, maxRetryCount),
        (error, retryCount) -> retryCount)
        .flatMap(retryCount -> Observable.timer((long) Math.pow(delay, retryCount), unit));
  }
}
