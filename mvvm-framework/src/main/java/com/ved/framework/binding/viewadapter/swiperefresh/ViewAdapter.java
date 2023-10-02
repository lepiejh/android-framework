package com.ved.framework.binding.viewadapter.swiperefresh;

import com.ved.framework.binding.command.BindingCommand;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;


/**
 * Created by ved on 2017/6/18.
 */
public class ViewAdapter {
    //下拉刷新命令
    @BindingAdapter({"onRefreshCommand"})
    public static void onRefreshCommand(SmartRefreshLayout swipeRefreshLayout, final BindingCommand onRefreshCommand) {
        swipeRefreshLayout.setOnRefreshListener(refreshLayout -> {
            if (onRefreshCommand != null) {
                onRefreshCommand.execute();
            }
        });
    }

    //加载更多命令
    @BindingAdapter({"onLoadMoreCommand"})
    public static void onLoadMoreCommand(SmartRefreshLayout swipeRefreshLayout, final BindingCommand onLoadMoreCommand) {

        swipeRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if (onLoadMoreCommand != null) {
                onLoadMoreCommand.execute();
            }
        });
    }

}
