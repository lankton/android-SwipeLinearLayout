package cn.lankton.swipelinearlayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.lankton.swipelinearlayout.lib.SwipeLinearLayout;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    MyAdapter adapter;
    List<SwipeLinearLayout> swipeLinearLayouts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        adapter = new MyAdapter(this);
        listView.setAdapter(adapter);
    }


    class MyAdapter extends BaseAdapter implements SwipeLinearLayout.OnSwipeListener {
        Context mContext;
        LayoutInflater inflater;
        public MyAdapter(Context context) {
            mContext = context;
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return 23;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (null == convertView) {
                convertView = inflater.inflate(R.layout.item_list, null);
                holder = new ViewHolder(convertView);
                swipeLinearLayouts.add(holder.sll);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            // 滚到收缩状态
            holder.sll.scrollTo(0,0);
            final ViewHolder finalHolder = holder;
            holder.ll_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "click item " + position, Toast.LENGTH_SHORT).show();
                    finalHolder.sll.scrollAuto(SwipeLinearLayout.DIRECTION_SHRINK);
                }
            });
            holder.tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "delete item " + position, Toast.LENGTH_SHORT).show();
                    finalHolder.sll.scrollAuto(SwipeLinearLayout.DIRECTION_SHRINK);
                }
            });
            return convertView;
        }


        @Override
        public void onDirectionJudged(SwipeLinearLayout thisSll, boolean isHorizontal) {
            if (false == isHorizontal) {
                for (SwipeLinearLayout sll : swipeLinearLayouts) {
                    if (null == sll) {
                        continue;
                    }
                    sll.scrollAuto(SwipeLinearLayout.DIRECTION_SHRINK);
                }
            } else {
                for (SwipeLinearLayout sll : swipeLinearLayouts) {
                    if (null == sll) {
                        continue;
                    }
                    if (!sll.equals(thisSll)) {
                        //划开一个sll， 其他收缩
                        sll.scrollAuto(SwipeLinearLayout.DIRECTION_SHRINK);
                    }
                }
            }
        }


        class ViewHolder {
            SwipeLinearLayout sll;
            View ll_left;
            View tv_delete;

            public ViewHolder(View v) {
                sll = (SwipeLinearLayout) v.findViewById(R.id.sll);
                ll_left = v.findViewById(R.id.ll_left);
                tv_delete = v.findViewById(R.id.tv_delete);
                sll.setOnSwipeListener(MyAdapter.this);
            }

        }
    }


}
