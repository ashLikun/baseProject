package com.doludolu.baseproject.code.iview;

/**
 * 作者　　: 李坤
 * 创建时间:2017/8/26 0026　0:02
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：设配器数据改变
 */

public interface INotifyView {
    public void notifyChanged();

    public interface INotifyItemChangView {
        public void notifyItemChanged(int position);
    }

    public interface INotifyItemRemovedView {
        public void notifyItemRemoved(int position);
    }

    public interface INotifyItemAddView {
        public void notifyItemAdd(int position);
    }

}
