package com.sleepingbear.znnews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;


public class NewsFragment extends Fragment {
    private View  mainView;
    private ListView listView;
    private NewsAdapter adapter;

    public NewsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_news, container, false);

        listView = (ListView)mainView.findViewById(R.id.my_f_news_lv);

        ArrayList<NewsVo> items = new ArrayList<>();
        int idx = 1;
        items.add(new NewsVo("E" + idx++, "Life & Health Newspaper - 종합일간지"));
        items.add(new NewsVo("E" + idx++, "Youth Newspaper - 종합일간지"));
        items.add(new NewsVo("E" + idx++, "Nhan Dan Newspaper - 종합일간지"));
        items.add(new NewsVo("E" + idx++, "Lao Dong Newspaper - 종합일간지"));
        items.add(new NewsVo("E" + idx++, "Yan News - 종합일간지"));
        items.add(new NewsVo("E" + idx++, "Vietnam News express - 경제일간지"));
        items.add(new NewsVo("E" + idx++, "Vietnam.net"));
        items.add(new NewsVo("E" + idx++, "Vietnam Economic Times - 경제주간지"));
        items.add(new NewsVo("E" + idx++, "Kinh te Saigon - 경제주간지"));
        items.add(new NewsVo("E" + idx++, "Life Style Magazine (Dep) - 종합주간지"));
        items.add(new NewsVo("E" + idx++, "Vietnam Television - 라디오/TV"));

        adapter = new NewsAdapter(getContext(), 0, items);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(itemClickListener);

        AdView av = (AdView)mainView.findViewById(R.id.adView);
        AdRequest adRequest = new  AdRequest.Builder().build();
        av.loadAd(adRequest);

        return mainView;
    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            NewsVo cur = (NewsVo) adapter.getItem(position);

            DicUtils.dicLog(cur.getName());

            Intent intent = new Intent(getActivity().getApplication(), WebViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("kind", cur.getKind());
            intent.putExtras(bundle);

            getActivity().startActivityForResult(intent, CommConstants.a_news);
        }
    };

    private class NewsVo {
        private String kind;
        private String name;

        public NewsVo(String kind, String name) {
            this.kind = kind;
            this.name = name;
        }

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private class NewsAdapter extends ArrayAdapter<NewsVo> {
        private ArrayList<NewsVo> items;

        public NewsAdapter(Context context, int textViewResourceId, ArrayList<NewsVo> objects) {
            super(context, textViewResourceId, objects);
            this.items = objects;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.fragment_news_item, null);
            }

            ((TextView)v.findViewById(R.id.my_f_tv_newname)).setText(items.get(position).getName());

            return v;
        }
    }

}
