package com.doludolu.baseproject.code.iview;

/**
 * Created by yang on 2016/9/6.
 */

public interface IProgressView {
    /**
     * 进度的回调
     *
     * @param progress 百分比
     * @param done     完成
     * @param isUpdate 是否是上传
     */
    void upLoading(int progress, boolean done, boolean isUpdate, boolean isCompress);

    void dismissProgressDialog();

}
