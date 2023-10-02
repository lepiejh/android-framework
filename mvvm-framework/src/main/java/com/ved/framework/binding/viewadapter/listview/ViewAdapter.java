package com.ved.framework.binding.viewadapter.listview;

import android.widget.AbsListView;
import android.widget.ListView;

import com.ved.framework.binding.command.BindingCommand;
import com.ved.framework.entity.ListViewScrollDataWrapper;

import java.util.concurrent.TimeUnit;

import androidx.databinding.BindingAdapter;
import io.reactivex.rxjava3.subjects.PublishSubject;

/**
 * Created by ved on 2017/6/18.
 */
public final class ViewAdapter {

    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"onScrollChangeCommand", "onScrollStateChangedCommand"}, requireAll = false)
    public static void onScrollChangeCommand(final ListView listView,
                                             final BindingCommand<ListViewScrollDataWrapper> onScrollChangeCommand,
                                             final BindingCommand<Integer> onScrollStateChangedCommand) {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int scrollState;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.scrollState = scrollState;
                if (onScrollStateChangedCommand != null) {
                    onScrollStateChangedCommand.execute(scrollState);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (onScrollChangeCommand != null) {
                    onScrollChangeCommand.execute(new ListViewScrollDataWrapper(scrollState, firstVisibleItem, visibleItemCount, totalItemCount));
                }
            }
        });

    }


    @BindingAdapter(value = {"onItemClickCommand"}, requireAll = false)
    public static void onItemClickCommand(final ListView listView, final BindingCommand<Integer> onItemClickCommand) {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (onItemClickCommand != null) {
                onItemClickCommand.execute(position);
            }
        });
    }


    @BindingAdapter({"onLoadMoreCommand"})
    public static void onLoadMoreCommand(final ListView listView, final BindingCommand<Integer> onLoadMoreCommand) {
        listView.setOnScrollListener(new OnScrollListener(listView, onLoadMoreCommand));

    }

    public static class OnScrollListener implements AbsListView.OnScrollListener {
        private PublishSubject<Integer> methodInvoke = PublishSubject.create();
        private BindingCommand<Integer> onLoadMoreCommand;
        private ListView listView;

        public OnScrollListener(ListView listView, final BindingCommand<Integer> onLoadMoreCommand) {
            this.onLoadMoreCommand = onLoadMoreCommand;
            this.listView = listView;
            methodInvoke.throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe(integer -> onLoadMoreCommand.execute(integer));
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem + visibleItemCount >= totalItemCount
                    && totalItemCount != 0
                    && totalItemCount != listView.getHeaderViewsCount()
                    + listView.getFooterViewsCount()) {
                if (onLoadMoreCommand != null) {
                    methodInvoke.onNext(totalItemCount);
                }
            }
        }
    }
}
