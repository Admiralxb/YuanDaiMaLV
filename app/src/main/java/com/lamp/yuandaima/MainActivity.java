package com.lamp.yuandaima;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lamp.yuandaima.entity.IndexRequestModel;
import com.lamp.yuandaima.entity.IndexRequestParamModel;
import com.lamp.yuandaima.okhttputils.OkHttpUtils;
import com.lamp.yuandaima.okhttputils.callback.StringCallback;
import com.lamp.yuandaima.ui.BaseActivity;
import com.lamp.yuandaima.utils.XDHRequestUtils;
import com.lamp.yuandaima.view.RotationImageView;
import com.lamp.yuandaima.xlistview.XListView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import okhttp3.Call;

public class MainActivity extends BaseActivity implements XListView.IXListViewListener, AdapterView.OnItemClickListener {

    @ViewInject(R.id.lv_index_list)
    private XListView mListView;

    private MyAdapter mAdapter;
    private ArrayList<String> items = new ArrayList<String>();
    private Handler mHandler;
    private int start = 0;
    private static int refreshCnt = 0;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        geneItems();
        initData();
        initView();


    }

    private void geneItems() {
        for (int i = 0; i != 20; ++i) {
            items.add("refresh cnt " + (++start));
        }
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                start = ++refreshCnt;
                items.clear();
                geneItems();
                // mAdapter.notifyDataSetChanged();
                mAdapter = new MyAdapter();
                mListView.setAdapter(mAdapter);
                onLoad();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                geneItems();
                mAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }


    private void initView() {

        x.view().inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        RotationImageView rotationImageView = new RotationImageView(this);
        mListView.addHeaderView(rotationImageView);
        mListView.setPullLoadEnable(true);
        mAdapter = new MyAdapter();
        mHandler = new Handler();
        mListView.setAdapter(mAdapter);
        mListView.setXListViewListener(this);
        mListView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    private void initData() {

        String url = "http://api.itxdh.net/index";
        StringBuffer sb = new StringBuffer();


        IndexRequestModel indexRequestModel = new IndexRequestModel(System.currentTimeMillis() + "", "1.0", "0", new IndexRequestParamModel("", "", "1"));
        String sss = XDHRequestUtils.getMD5("index" + indexRequestModel.getTime() + indexRequestModel.getGuid() + "1");


        System.out.println("sss == " + sss);
        indexRequestModel.setSignature(sss);
        System.out.println("gson == " + new Gson().toJson(indexRequestModel));
        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(indexRequestModel))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        System.out.println("response == " + response);

                    }
                });


//        String url = "http://test-mall.dongqiudi.com/api/index";
//        OkHttpUtils
//                .get()
//                .url(url)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e) {
//
//                    }
//
//                    @Override
//                    public void onResponse(String response) {
//                        System.out.println("response == " + response);
//                    }
//                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh = null;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.view_listview_item, null);
                vh = new ViewHolder();
                vh.mClassIcon = (ImageView) convertView.findViewById(R.id.iv_listitem_icon);
                vh.mTeacherImg = (ImageView) convertView.findViewById(R.id.iv_listitem_teacherImg);
                vh.mTextViewClassTitle = (TextView) convertView.findViewById(R.id.tv_listitem_classTitle);
                vh.mTextViewClassIntroduction = (TextView) convertView.findViewById(R.id.tv_listitem_classIntroduction);
                vh.mTextViewStudentCount = (TextView) convertView.findViewById(R.id.tv_listitem_studentCount);

                convertView.setTag(vh);
            }

            vh = (ViewHolder) convertView.getTag();

            return convertView;
        }
    }

    class ViewHolder {
        ImageView mClassIcon;
        TextView mTextViewClassTitle;
        TextView mTextViewClassIntroduction;
        ImageView mTeacherImg;
        TextView mTextViewStudentCount;
    }
}












