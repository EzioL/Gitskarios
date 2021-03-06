package com.alorma.github.injector.module.repository;

import com.alorma.github.sdk.bean.info.RepoInfo;
import core.datasource.CloudDataSource;
import core.datasource.RestWrapper;
import core.datasource.SdkItem;
import core.repository.RepoActionsService;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;

public class RepoActionUnWatchDatasource extends CloudDataSource<RepoInfo, Boolean> {
  public RepoActionUnWatchDatasource(RestWrapper restWrapper) {
    super(restWrapper);
  }

  @Override
  protected Observable<SdkItem<Boolean>> execute(SdkItem<RepoInfo> request, RestWrapper service) {
    RepoActionsService actionsService = service.get();

    return Observable.defer(() -> {

      Call<ResponseBody> call = actionsService.unwatchRepo(request.getK().owner, request.getK().name);

      try {
        Response<ResponseBody> response = call.execute();

        return Observable.just(response.isSuccessful());
      } catch (IOException e) {
        return Observable.just(false);
      }
    }).map(SdkItem::new);
  }
}
