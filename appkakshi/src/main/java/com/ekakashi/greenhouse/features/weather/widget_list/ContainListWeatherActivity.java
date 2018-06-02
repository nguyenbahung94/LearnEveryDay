package com.ekakashi.greenhouse.features.weather.widget_list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.features.weather.NumberOfBlock;
import com.ekakashi.greenhouse.features.weather.object_weather.EnumWeather;
import com.ekakashi.greenhouse.features.weather.object_weather.ObjectTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nbhung on 3/6/2018.
 */

public class ContainListWeatherActivity extends AppCompatActivity {
    private LinearLayout llContainWeather;

    public static void start(Context context) {
        Intent starter = new Intent(context, ContainListWeatherActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_contain_list_weather);
        llContainWeather = findViewById(R.id.llContainWeather);

        findViewById(R.id.imvClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        List<ObjectTemplate> list = new ArrayList<>();
        list.add(new ObjectTemplate("123", EnumWeather.WEATHER_TEMPERATURE, "123", "123", "123", new ArrayList<String>(), 123, 1));
        list.add(new ObjectTemplate("123", EnumWeather.AMOUNT_OF_INSOLATION, "123", "123", "123", new ArrayList<String>(), 123, 2));
        list.add(new ObjectTemplate("123", EnumWeather.HUMIDITY, "123", "123", "123", new ArrayList<String>(), 123, 1));
        list.add(new ObjectTemplate("123", EnumWeather.PRECIPITATION, "123", "123", "123", new ArrayList<String>(), 123, 1));
        list.add(new ObjectTemplate("123", EnumWeather.PRECIPITATION, "123", "123", "123", new ArrayList<String>(), 123, 1));

        addElement(list);
    }

    public void addElement(List<ObjectTemplate> templateList) {
        for (int j = 0; j < templateList.size(); j++) {
            ObjectTemplate firstObject = templateList.get(j);
            if (j + 1 <= templateList.size() - 1) {
                if (firstObject.getNumberOfColumns() == 1) {
                    ObjectTemplate secondObject = templateList.get(j + 1);
                    if (secondObject.getNumberOfColumns() == 1) {
                        // TODO: add two template at the same time
                        j++;
                        addTwoLayout(2, firstObject, secondObject);
                    } else {
                        if (secondObject.getNumberOfColumns() == 2) {
                            // TODO: add one template
                            addTwoLayout(1, firstObject, null);
                        }
                    }
                } else {
                    if (firstObject.getNumberOfColumns() == 2) {
                        // TODO: add graph
                        addTwoLayout(3, firstObject, null);
                    }
                }
            } else {
                if (firstObject.getNumberOfColumns() == 1) {
                    // TODO: add one template
                    addTwoLayout(1, firstObject, null);
                }
                if (firstObject.getNumberOfColumns() == 2) {
                    // TODO: add graph
                    addTwoLayout(3, firstObject, null);
                }
            }
        }

    }

    @SuppressLint("InflateParams")
    public void addTwoLayout(int number, ObjectTemplate firstObject, ObjectTemplate secondObject) {
        View viewContainTemplate;
        View viewItem;
        View chartView;
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      /*  if (inflater == null) {
            return;
        }
        viewContainTemplate = inflater.inflate(R.layout.item_weather_inlist, null);
        LinearLayout llItemWeather = viewContainTemplate.findViewById(R.id.llItemWeather);
        viewItem = inflater.inflate(R.layout.one_item_weather, null);
        chartView = inflater.inflate(R.layout.activity_graph, null);
        switch (number) {
            case 1:
                viewItem.findViewById(R.id.llOneItemWeather).setVisibility(View.VISIBLE);
                viewItem.findViewById(R.id.llTwoItemWeather).setVisibility(View.GONE);
                ((LinearLayout) viewItem.findViewById(R.id.llOneItemWeather)).addView(NumberOfBlock.getUIWeather(firstObject.getTemplateType(), this));
                llItemWeather.addView(viewItem);
                llContainWeather.addView(viewContainTemplate);
                break;
            case 2:
                viewItem.findViewById(R.id.llOneItemWeather).setVisibility(View.GONE);
                viewItem.findViewById(R.id.llTwoItemWeather).setVisibility(View.VISIBLE);
                ((LinearLayout) viewItem.findViewById(R.id.llLeft)).addView(NumberOfBlock.getUIWeather(firstObject.getTemplateType(), this));
                ((LinearLayout) viewItem.findViewById(R.id.llRight)).addView(NumberOfBlock.getUIWeather(secondObject.getTemplateType(), this));
                llItemWeather.addView(viewItem);
                llContainWeather.addView(viewContainTemplate);
                break;
            case 3:
                llItemWeather.addView(chartView);
                llContainWeather.addView(viewContainTemplate);
                break;
            default:
                break;
        }*/
    }
}
