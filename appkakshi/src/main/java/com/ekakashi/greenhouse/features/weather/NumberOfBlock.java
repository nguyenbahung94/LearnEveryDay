package com.ekakashi.greenhouse.features.weather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.DateTimeNow;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.template_recipe.Data;
import com.ekakashi.greenhouse.database.model_server_response.template_recipe.GraphV3YAxis;
import com.ekakashi.greenhouse.features.weather.object_weather.EnumWeather;
import com.ekakashi.greenhouse.features.weather.object_weather.ObjectCommonTemplateRecipe;
import com.ekakashi.greenhouse.features.weather.object_weather.ObjectDataForWidgetGraph;
import com.ekakashi.greenhouse.features.weather.object_weather.graphData;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nbhung on 3/6/2018.
 */

public class NumberOfBlock {
    private Context context;
    private static String TAG = "NumbnerOfBlock";

    public NumberOfBlock(Context context) {
        this.context = context;
    }

    public View createBlock(ObjectCommonTemplateRecipe objectCommonTemplateRecipe) {
        List<Data> list = objectCommonTemplateRecipe.getdataTemplateRecipe();

        View chart;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return null;
        }
        chart = inflater.inflate(R.layout.activity_graph, null);
        View myView = null;
        int columns = 0;
        int template = 0;
        for (int tam = 0; tam < list.size(); tam++) {
            Data ss = list.get(tam);
            template++;
            columns += ss.getNumberOfColumn();
            if (list.size() == 1) {
                if (template == 1 && columns == 2) {

                    if (ss.getGraph() != null) {
                        myView = getViewBlock(Utils.Weather.ONE_WIDGET, Utils.Weather.ONE_WIDGET_TWO_COLUMN, chart, (graphData) objectCommonTemplateRecipe.getListMapObject().get(ss.getId()), null, list);
                        // TODO: No.8
                        // TODO: add graph here no need weather
                    } else {
                        if (ss.getWidget() != null) {
                            //add widget have column=2
                        }
                    }
                    break;
                }
                if (template == 1 && columns == 1) {
                    // add weather here with column=1
                    // TODO: No.4 
                    myView = getViewBlock(Utils.Weather.ONE_WIDGET, Utils.Weather.ONE_COLUMN, chart, null, null, list);
                    addViewWeather(myView, ss, null, null, null, (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(ss.getId()), null, null, null, 1);
                    break;
                }
            }
            if (list.size() == 2) {
                if (template == 2 && columns == 2) {
                    // TODO: No.3
                    //add two template here
                    myView = getViewBlock(Utils.Weather.TWO_WIDGET, Utils.Weather.ONE_COLUMN, chart, null, null, list);
                    // addViewWeather(myView, objectCommonTemplateRecipe, 2);
                    addViewWeather(myView, list.get(0), list.get(1), null, null, (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(0).getId()), (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(1).getId()), null, null, 2);
                    break;
                }
                if (template == 2 && columns == 3) {
                    // TODO: No.5
                    // TODO: one template and one graph here

                    // TODO: 4/12/2018  add one template with one graph or one template one columns with one template have two columns=2
                    if (ss.getGraph() != null) {
                        myView = getViewBlock(Utils.Weather.ONE_WIDGET, Utils.Weather.TWO_COLUMN, chart, (graphData) objectCommonTemplateRecipe.getListMapObject().get(ss.getId()), null, list);
                        //addViewWeather(myView, objectCommonTemplateRecipe, 1);
                        addViewWeather(myView, list.get(0), null, null, null, (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(0).getId()), null, null, null, 1);
                    } else {
                        // TODO: 4/12/2018 need to create new layout to show graph above weather ,missing right now
                        if (ss.getWidget() != null) {
                            myView = getViewBlock(Utils.Weather.ONE_WIDGET, Utils.Weather.GRAPH_ABOVE_WEATHER, chart, (graphData) objectCommonTemplateRecipe.getListMapObject().get(list.get(0).getId()), null, list);
                            addViewWeather(myView, list.get(1), null, null, null, (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(1).getId()), null, null, null, 5);
                        }
                    }

                    break;
                }
                if (template == 2 && columns == 4) {
                    //add two graph here or template have columns=2
                    myView = getViewBlock(Utils.Weather.TWO_WIDGET, Utils.Weather.TWO_COLUMN, chart, (graphData) objectCommonTemplateRecipe.getListMapObject().get(list.get(0).getId()), (graphData) objectCommonTemplateRecipe.getListMapObject().get(list.get(1).getId()), list);
                    // TODO: 4/12/2018 create switch case for two graph
                }
            }

            if (list.size() == 3) {
                if (template == 3 && columns == 3) {
                    // TODO: No.1
                    // TODO: add three template here
                    myView = getViewBlock(Utils.Weather.THREE_WIDGET, Utils.Weather.ONE_COLUMN, chart, null, null, list);
                    addViewWeather(myView, list.get(0), list.get(1), list.get(2), null, (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(0).getId()), (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(1).getId()), (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(2).getId()), null, 3);
                    break;
                }
                if (template == 2 && columns == 3) {
                    // TODO: No.5
                    // TODO: add one graph and one template

                    if (ss.getGraph() != null) {
                        myView = getViewBlock(Utils.Weather.ONE_WIDGET, Utils.Weather.TWO_COLUMN, chart, (graphData) objectCommonTemplateRecipe.getListMapObject().get(ss.getId()), null, list);
                        addViewWeather(myView, list.get(0), null, null, null, (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(0).getId()), null, null, null, 1);
                    } else {
                        if (ss.getWidget() != null) {
                            myView = getViewBlock(Utils.Weather.ONE_WIDGET, Utils.Weather.GRAPH_ABOVE_WEATHER, chart, (graphData) objectCommonTemplateRecipe.getListMapObject().get(list.get(0).getId()), null, list);
                            addViewWeather(myView, list.get(1), null, null, null, (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(1).getId()), null, null, null, 5);
                        }
                    }
                    break;
                }
                if (template == 2 && columns == 4) {
                    // add two graph or two template have column=2
                    myView = getViewBlock(Utils.Weather.TWO_WIDGET, Utils.Weather.TWO_COLUMN, chart, (graphData) objectCommonTemplateRecipe.getListMapObject().get(list.get(0).getId()), (graphData) objectCommonTemplateRecipe.getListMapObject().get(list.get(1).getId()), list);
                }
                if (template == 3 && columns == 4) {
                    // TODO: No.7
                    // TODO: need to create new layout to show data
                    if (list.get(0).getGraph() != null) {
                        myView = getViewBlock(Utils.Weather.ONE_WIDGET, Utils.Weather.GRAPH_ABOVE_TWO_WEATHER, chart, (graphData) objectCommonTemplateRecipe.getListMapObject().get(list.get(0).getId()), null, list);
                        addViewWeather(myView, list.get(1), list.get(2), null, null, (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(1).getId()), (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(2).getId()), null, null, 6);
                    } else {
                        if (list.get(1).getGraph() != null) {
                            myView = getViewBlock(Utils.Weather.ONE_WIDGET, Utils.Weather.ONE_WIDGET_TWO_COLUMN_AND_TWO_WIDGET_ONE_COLUMN, chart, (graphData) objectCommonTemplateRecipe.getListMapObject().get(list.get(1).getId()), null, list);
                            addViewWeather(myView, list.get(0), null, null, null, (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(0).getId()), null, null, null, 1);
                        } else {
                            if (list.get(2).getGraph() != null) {
                                myView = getViewBlock(Utils.Weather.ONE_WIDGET, Utils.Weather.ONE_WIDGET_TWO_COLUMN_AND_TWO_WIDGET_ONE_COLUMN, chart, (graphData) objectCommonTemplateRecipe.getListMapObject().get(list.get(2).getId()), null, list);
                                addViewWeather(myView, list.get(0), list.get(1), null, null, (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(0).getId()), (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(1).getId()), null, null, 2);
                            }
                        }
                    }
                    // TODO: add two template and one graph here
                    break;
                }
            }

            if (list.size() >= 4) {
                if (template == 2 && columns == 4) {
                    //add two graph here or two template have column=2
                    myView = getViewBlock(Utils.Weather.TWO_WIDGET, Utils.Weather.TWO_COLUMN, chart, (graphData) objectCommonTemplateRecipe.getListMapObject().get(list.get(tam - 1).getId()), (graphData) objectCommonTemplateRecipe.getListMapObject().get(ss.getId()), list);
                    break;
                }
                if (template == 2 && columns == 3) {
                    if (list.get(tam + 1).getNumberOfColumn() == 2) {
                        // TODO: No.5
                        // TODO: add one graph and one template

                        if (ss.getGraph() != null) {
                            myView = getViewBlock(Utils.Weather.ONE_WIDGET, Utils.Weather.TWO_COLUMN, chart, (graphData) objectCommonTemplateRecipe.getListMapObject().get(ss.getId()), null, list);
                            addViewWeather(myView, list.get(0), null, null, null, (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(0).getId()), null, null, null, 1);
                        } else {
                            if (ss.getWidget() != null) {
                                myView = getViewBlock(Utils.Weather.ONE_WIDGET, Utils.Weather.GRAPH_ABOVE_WEATHER, chart, (graphData) objectCommonTemplateRecipe.getListMapObject().get(list.get(0).getId()), null, list);
                                addViewWeather(myView, list.get(1), null, null, null, (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(1).getId()), null, null, null, 5);
                            }
                        }

                    } else {
                        // TODO: No.7
                        if (list.get(tam).getGraph() != null && list.get(tam + 1).getNumberOfColumn() == 1) {
                            myView = getViewBlock(Utils.Weather.ONE_WIDGET, Utils.Weather.ONE_WIDGET_TWO_COLUMN_AND_TWO_WIDGET_ONE_COLUMN, chart, (graphData) objectCommonTemplateRecipe.getListMapObject().get(list.get(tam).getId()), null, list);
                            //   addViewWeather(myView, list.get(tam - 1), list.get(tam + 1), null, null, (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(tam - 1).getId()), (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(tam + 1).getId()), null, null, 2);

                            addViewWeather(myView, list.get(tam - 1), null, null, null, (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(tam - 1).getId()), null, null, null, 1);
                        } else {
                            myView = getViewBlock(Utils.Weather.ONE_WIDGET, Utils.Weather.ONE_WIDGET_TWO_COLUMN_AND_TWO_WIDGET_ONE_COLUMN, chart, (graphData) objectCommonTemplateRecipe.getListMapObject().get(list.get(0).getId()), null, list);
                            addViewWeather(myView, list.get(tam), list.get(tam + 1), null, null, (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(tam).getId()), (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(tam + 1).getId()), null, null, 6);
                        }
                    }
                    break;
                }
                if (template == 3 && columns == 3) {
                    if (list.get(tam + 1).getNumberOfColumn() == 2) {
                        //No.1
                        myView = getViewBlock(Utils.Weather.THREE_WIDGET, Utils.Weather.ONE_COLUMN, chart, null, null, list);
                        addViewWeather(myView, list.get(0), list.get(1), list.get(2), null, (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(0).getId()), (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(1).getId()), (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(2).getId()), null, 3);
                        break;
                    }
                }
                if (template == 3 && columns == 4) {
                    // TODO: No.7
                    if (list.get(0).getGraph() != null) {
                        myView = getViewBlock(Utils.Weather.ONE_WIDGET, Utils.Weather.ONE_WIDGET_TWO_COLUMN_AND_TWO_WIDGET_ONE_COLUMN, chart, (graphData) objectCommonTemplateRecipe.getListMapObject().get(list.get(0).getId()), null, list);
                        addViewWeather(myView, list.get(1), list.get(2), null, null, (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(1).getId()), (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(2).getId()), null, null, 6);
                    } else {
                        if (list.get(1).getGraph() != null) {
                            myView = getViewBlock(Utils.Weather.ONE_WIDGET, Utils.Weather.ONE_WIDGET_TWO_COLUMN_AND_TWO_WIDGET_ONE_COLUMN, chart, (graphData) objectCommonTemplateRecipe.getListMapObject().get(list.get(1).getId()), null, list);
                            addViewWeather(myView, list.get(0), null, null, null, (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(0).getId()), null, null, null, 1);
                        } else {
                            if (list.get(2).getGraph() != null) {
                                myView = getViewBlock(Utils.Weather.ONE_WIDGET, Utils.Weather.ONE_WIDGET_TWO_COLUMN_AND_TWO_WIDGET_ONE_COLUMN, chart, (graphData) objectCommonTemplateRecipe.getListMapObject().get(list.get(2).getId()), null, list);
                                addViewWeather(myView, list.get(0), list.get(1), null, null, (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(0).getId()), (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(1).getId()), null, null, 2);
                            }
                        }
                    }
                    // TODO: add two template and one graph here
                    break;
                }
                if (template == 4 && columns == 4) {
                    // TODO: No.2
                    myView = getViewBlock(Utils.Weather.FOUR_WIDGET, Utils.Weather.ONE_COLUMN, chart, null, null, list);
                    addViewWeather(myView, list.get(0), list.get(1), list.get(2), list.get(3), (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(0).getId()), (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(1).getId()), (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(2).getId()), (ObjectDataForWidgetGraph.WidgetDataResponse) objectCommonTemplateRecipe.getListMapObject().get(list.get(3).getId()), 4);
                    break;
                }

            }
        }
        return myView;
    }

    private void addViewWeather(View parent, Data data1, Data data2, Data data3, Data data4, ObjectDataForWidgetGraph.WidgetDataResponse widgetDataResponse1, ObjectDataForWidgetGraph.WidgetDataResponse widgetDataResponse2, ObjectDataForWidgetGraph.WidgetDataResponse widgetDataResponse3, ObjectDataForWidgetGraph.WidgetDataResponse widgetDataResponse4, int countView) {

        switch (countView) {
            case 1:
                View viewWeather = getUIWeather(data1.getTypeOfWeather(), widgetDataResponse1.getData(), context, data1.getName());
                if (parent != null) {
                    ((LinearLayout) parent.findViewById(R.id.widget1)).addView(viewWeather);
                }
                break;
            case 2:
                if (parent != null) {
                    View template1 = getUIWeather(data1.getTypeOfWeather(), widgetDataResponse1.getData(), context, data1.getName());
                    View template2 = getUIWeather(data2.getTypeOfWeather(), widgetDataResponse2.getData(), context, data2.getName());
                    ((LinearLayout) parent.findViewById(R.id.widget1)).addView(template1);
                    ((LinearLayout) parent.findViewById(R.id.widget2)).addView(template2);

                }
                break;
            case 3:
                if (parent != null) {
                    View template1 = getUIWeather(data1.getTypeOfWeather(), widgetDataResponse1.getData(), context, data1.getName());
                    View template2 = getUIWeather(data2.getTypeOfWeather(), widgetDataResponse2.getData(), context, data2.getName());
                    View template3 = getUIWeather(data3.getTypeOfWeather(), widgetDataResponse3.getData(), context, data3.getName());
                    ((LinearLayout) parent.findViewById(R.id.widget1)).addView(template1);
                    ((LinearLayout) parent.findViewById(R.id.widget3)).addView(template2);
                    ((LinearLayout) parent.findViewById(R.id.widget4)).addView(template3);

                }
                break;
            case 4:
                if (parent != null) {
                    View template1 = getUIWeather(data1.getTypeOfWeather(), widgetDataResponse1.getData(), context, data1.getName());
                    View template2 = getUIWeather(data2.getTypeOfWeather(), widgetDataResponse2.getData(), context, data2.getName());
                    View template3 = getUIWeather(data3.getTypeOfWeather(), widgetDataResponse3.getData(), context, data3.getName());
                    View template4 = getUIWeather(data4.getTypeOfWeather(), widgetDataResponse4.getData(), context, data4.getName());
                    ((LinearLayout) parent.findViewById(R.id.widget1)).addView(template1);
                    ((LinearLayout) parent.findViewById(R.id.widget2)).addView(template2);
                    ((LinearLayout) parent.findViewById(R.id.widget3)).addView(template3);
                    ((LinearLayout) parent.findViewById(R.id.widget4)).addView(template4);
                }
                break;
            case 5:
                //No.5
                //add graph a bow weather
                View viewWeather5 = getUIWeather(data1.getTypeOfWeather(), widgetDataResponse1.getData(), context, data1.getName());
                if (parent != null) {
                    ((LinearLayout) parent.findViewById(R.id.widget3)).addView(viewWeather5);
                }
                break;
            case 6:
                //No.7
                //add graph a bow weather
                if (parent != null) {
                    View template1 = getUIWeather(data1.getTypeOfWeather(), widgetDataResponse1.getData(), context, data1.getName());
                    View template2 = getUIWeather(data2.getTypeOfWeather(), widgetDataResponse2.getData(), context, data2.getName());
                    ((LinearLayout) parent.findViewById(R.id.widget3)).addView(template1);
                    ((LinearLayout) parent.findViewById(R.id.widget4)).addView(template2);
                }
                break;
            default:
                break;
        }
    }

    @SuppressLint("InflateParams")
    public static View getUIWeather(EnumWeather enumWeather, ObjectDataForWidgetGraph.WidgetData widgetData, Context context, String name) {
        View myView = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return null;
        }
        switch (enumWeather) {
            case AMOUNT_OF_INSOLATION:
                myView = inflater.inflate(R.layout.activity_amountof_insolation, null);
                ((TextView) myView.findViewById(R.id.tvName)).setText(name);
                // TODO: 4/4/2018 missing field amount isolation
                // ((TextView) myView.findViewById(R.id.currentAmount)).setText(widgetData.get);
                break;
            case HUMIDITY:
                myView = inflater.inflate(R.layout.activity_humidity, null);
                ((TextView) myView.findViewById(R.id.tvName)).setText(name);
                // TODO: 4/4/2018 need to get humidity here ,this field missing
                //  ((TextView) myView.findViewById(R.id.currentHumidity)).setText(widgetData.get);

                break;
            case PRECIPITATION:
                myView = inflater.inflate(R.layout.activity_precipitation, null);
                if (widgetData.getPrecipitation() != null) {
                    ((TextView) myView.findViewById(R.id.currentPrecipitation)).setText(widgetData.getPrecipitation());
                    ((TextView) myView.findViewById(R.id.tvName)).setText(name);
                }

                break;
            case WIND_VELOCITY:
                myView = inflater.inflate(R.layout.activity_wind_velocity, null);
                ((TextView) myView.findViewById(R.id.tvName)).setText(name);
                // TODO: 4/4/2018 missing field wind velocity
                // ((TextView) myView.findViewById(R.id.currentWindVelocity)).setText(widgetData.);
                break;
            case SUNRISE_SUNSET:
                myView = inflater.inflate(R.layout.activity_sunrise_sunset, null);
                ((TextView) myView.findViewById(R.id.tvName)).setText(name);
                if (widgetData.getSunRiseSunSet() != null && widgetData.getSunRiseSunSet().getSunRise() != null && widgetData.getSunRiseSunSet().getSunSet() != null) {
                    ((TextView) myView.findViewById(R.id.currentSunrise1)).setText(widgetData.getSunRiseSunSet().getSunRise());
                    ((TextView) myView.findViewById(R.id.currentSunrise2)).setText(widgetData.getSunRiseSunSet().getSunSet());
                }

                break;
            case WIND_DIRECTION:
                myView = inflater.inflate(R.layout.activity_wind_direction, null);
                // TODO: 4/5/2018 need to  rotate image
                ((TextView) myView.findViewById(R.id.tvName)).setText(name);
                ImageView imageView = myView.findViewById(R.id.imvWeather);
                RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 105.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(100);
                rotateAnimation.setFillAfter(true);
                imageView.startAnimation(rotateAnimation);
                break;
            case WEATHER_TEMPERATURE:
                myView = inflater.inflate(R.layout.activity_weather_temperature, null);
                ((TextView) myView.findViewById(R.id.tvName)).setText(name);
                if (widgetData.getTemperature() != null && widgetData.getTemperature().getCurrentTemperature() != null) {
                    ((TextView) myView.findViewById(R.id.currentTemperature)).setText(widgetData.getTemperature().getCurrentTemperature());
                    if (widgetData.getTemperature().getMaxTemperature() != null) {
                        ((TextView) myView.findViewById(R.id.maxTemperature)).setText(widgetData.getTemperature().getMaxTemperature());
                    }
                    if (widgetData.getTemperature().getMinTemperature() != null) {
                        ((TextView) myView.findViewById(R.id.minTemperature)).setText(widgetData.getTemperature().getMinTemperature());
                    }
                }

                break;
            case WEATHER_FORECAST_2DAY:
                myView = inflater.inflate(R.layout.activity_weather_forecast, null);
                ((TextView) myView.findViewById(R.id.tvName)).setText(name);
                if (widgetData.getForecast2Data() != null && widgetData.getForecast2Data().size() >= 1) {
                    ((TextView) myView.findViewById(R.id.tvDate)).setText(widgetData.getForecast2Data().get(0).getDateTime());
                    ((TextView) myView.findViewById(R.id.tvFirst)).setText(widgetData.getForecast2Data().get(0).getWeather());
                    ((TextView) myView.findViewById(R.id.tvSecond)).setText(widgetData.getForecast2Data().get(0).getTemperature().getCurrentTemperature());

                }
                if (widgetData.getForecast2Data() != null && widgetData.getForecast2Data().size() >= 2) {
                    ((TextView) myView.findViewById(R.id.tvDate1)).setText(widgetData.getForecast2Data().get(1).getDateTime());
                    ((TextView) myView.findViewById(R.id.tvFirst1)).setText(widgetData.getForecast2Data().get(1).getWeather());
                    ((TextView) myView.findViewById(R.id.tvSecond1)).setText(widgetData.getForecast2Data().get(1).getTemperature().getCurrentTemperature());
                }
                if (widgetData.getForecast2Data() != null && widgetData.getForecast2Data().size() >= 3) {
                    ((TextView) myView.findViewById(R.id.tvDate2)).setText(widgetData.getForecast2Data().get(2).getDateTime());
                    ((TextView) myView.findViewById(R.id.tvFirst2)).setText(widgetData.getForecast2Data().get(2).getWeather());
                    ((TextView) myView.findViewById(R.id.tvSecond2)).setText(widgetData.getForecast2Data().get(2).getTemperature().getCurrentTemperature());
                }
                break;
            case WEATHER_FORECAST_8DAY:
                myView = inflater.inflate(R.layout.activity_weather_forecast, null);
                ((TextView) myView.findViewById(R.id.tvName)).setText(name);
                // TODO: 4/4/2018 add fore cast 7 day here
                break;
            default:
                break;
        }
        return myView;
    }

    @SuppressLint("InflateParams")
    private View getViewBlock(int widget, int column, View chart, graphData datachart, graphData datachart2, List<Data> list) {
        View myView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return null;
        }
        myView = inflater.inflate(R.layout.activity_content_block, null);
        switch (widget) {
            case Utils.Weather.THREE_WIDGET:
                // TODO: show No.1
                //case 3 widget one column
                if (column == Utils.Weather.ONE_COLUMN) {
                    myView.findViewById(R.id.rlSecond).setVisibility(View.GONE);
                    myView.findViewById(R.id.rlFirst).setVisibility(View.GONE);
                    myView.findViewById(R.id.llSecond).setVisibility(View.VISIBLE);
                    myView.findViewById(R.id.llFirst).setVisibility(View.VISIBLE);
                    myView.findViewById(R.id.widget2).setVisibility(View.INVISIBLE);
                    myView.findViewById(R.id.widget1).setVisibility(View.VISIBLE);
                    myView.findViewById(R.id.widget3).setVisibility(View.VISIBLE);
                    myView.findViewById(R.id.widget4).setVisibility(View.VISIBLE);
                }

                break;
            case Utils.Weather.FOUR_WIDGET:
                // TODO: show No.2
                // case 4 widget one column
                if (column == Utils.Weather.ONE_COLUMN) {
                    myView.findViewById(R.id.rlSecond).setVisibility(View.GONE);
                    myView.findViewById(R.id.rlFirst).setVisibility(View.GONE);
                    myView.findViewById(R.id.llSecond).setVisibility(View.VISIBLE);
                    myView.findViewById(R.id.llFirst).setVisibility(View.VISIBLE);
                    myView.findViewById(R.id.widget2).setVisibility(View.VISIBLE);
                    myView.findViewById(R.id.widget1).setVisibility(View.VISIBLE);
                    myView.findViewById(R.id.widget3).setVisibility(View.VISIBLE);
                    myView.findViewById(R.id.widget4).setVisibility(View.VISIBLE);
                }
                break;
            case Utils.Weather.TWO_WIDGET:
                if (column == Utils.Weather.TWO_COLUMN) {
                    // TODO: show No.6
                    // two widget two column
                    myView.findViewById(R.id.rlSecond).setVisibility(View.VISIBLE);
                    myView.findViewById(R.id.rlFirst).setVisibility(View.VISIBLE);
                    myView.findViewById(R.id.llSecond).setVisibility(View.INVISIBLE);
                    myView.findViewById(R.id.llFirst).setVisibility(View.INVISIBLE);
                    ((RelativeLayout) myView.findViewById(R.id.rlFirst)).removeAllViews();
                    ((RelativeLayout) myView.findViewById(R.id.rlSecond)).removeAllViews();
                    ((RelativeLayout) myView.findViewById(R.id.rlFirst)).addView(chart);
                    CombinedChart combinedChart = chart.findViewById(R.id.CombinedChart);
                    View chartView = inflater.inflate(R.layout.activity_graph, null);
                    ((RelativeLayout) myView.findViewById(R.id.rlSecond)).addView(chartView);
                    CombinedChart combinedChartView = chartView.findViewById(R.id.CombinedChart);
                    for (Data ss : list) {
                        if (ss.getId() == datachart.getTemplatedId()) {
                            //test chartview
                            ((TextView) chartView.findViewById(R.id.tvNameGraph)).setText(ss.getName() + "");
                            setUpChart(combinedChart, datachart, 2, list, context, combinedChart, ss);
                        }
                        if (ss.getId() == datachart2.getTemplatedId()) {
                            ((TextView) chartView.findViewById(R.id.tvNameGraph)).setText(ss.getName() + "");
                            setUpChart(combinedChartView, datachart2, 2, list, context, combinedChartView, ss);
                        }
                    }
                } else {
                    // TODO: show No.3
                    // case 2 widget one column
                    if (column == Utils.Weather.ONE_COLUMN) {
                        myView.findViewById(R.id.rlSecond).setVisibility(View.GONE);
                        myView.findViewById(R.id.rlFirst).setVisibility(View.GONE);
                        myView.findViewById(R.id.llSecond).setVisibility(View.GONE);
                        myView.findViewById(R.id.llFirst).setVisibility(View.VISIBLE);
                        myView.findViewById(R.id.widget2).setVisibility(View.VISIBLE);
                        myView.findViewById(R.id.widget1).setVisibility(View.VISIBLE);
                    }

                }
                break;
            case Utils.Weather.ONE_WIDGET:

                if (column == Utils.Weather.TWO_COLUMN) {
                    // TODO: show No.5
                    // one widget one column and one widget two column
                    ((RelativeLayout) myView.findViewById(R.id.rlSecond)).removeAllViews();
                    myView.findViewById(R.id.rlSecond).setVisibility(View.VISIBLE);
                    myView.findViewById(R.id.rlFirst).setVisibility(View.GONE);
                    myView.findViewById(R.id.llSecond).setVisibility(View.GONE);
                    myView.findViewById(R.id.llFirst).setVisibility(View.VISIBLE);
                    myView.findViewById(R.id.widget2).setVisibility(View.INVISIBLE);
                    myView.findViewById(R.id.widget1).setVisibility(View.VISIBLE);
                    ((RelativeLayout) myView.findViewById(R.id.rlSecond)).addView(chart);
                    CombinedChart combinedChart = chart.findViewById(R.id.CombinedChart);
                    for (Data ss : list) {
                        if (ss.getId() == datachart.getTemplatedId()) {
                            ((TextView) chart.findViewById(R.id.tvNameGraph)).setText(ss.getName() + "");
                            setUpChart(combinedChart, datachart, 2, list, context, combinedChart, ss);
                        }
                    }
                } else {
                    if (column == Utils.Weather.ONE_WIDGET_TWO_COLUMN_AND_TWO_WIDGET_ONE_COLUMN) {
                        // TODO: show No.7
                        // 1 widget two column and two widgets one column
                        ((RelativeLayout) myView.findViewById(R.id.rlSecond)).removeAllViews();
                        myView.findViewById(R.id.rlSecond).setVisibility(View.VISIBLE);
                        ((RelativeLayout) myView.findViewById(R.id.rlSecond)).addView(chart);
                        CombinedChart combinedChart = chart.findViewById(R.id.CombinedChart);
                        for (Data ss : list) {
                            if (ss.getId() == datachart.getTemplatedId()) {
                                ((TextView) chart.findViewById(R.id.tvNameGraph)).setText(ss.getName() + "");
                                setUpChart(combinedChart, datachart, 2, list, context, combinedChart, ss);
                            }
                        }
                        myView.findViewById(R.id.rlFirst).setVisibility(View.GONE);
                        myView.findViewById(R.id.llSecond).setVisibility(View.GONE);
                        myView.findViewById(R.id.llFirst).setVisibility(View.VISIBLE);
                        myView.findViewById(R.id.widget2).setVisibility(View.VISIBLE);
                        myView.findViewById(R.id.widget1).setVisibility(View.VISIBLE);
                    } else {
                        if (column == Utils.Weather.ONE_WIDGET_TWO_COLUMN) {
                            // TODO: show No.8
                            // 1 widget two column
                            myView.findViewById(R.id.rlSecond).setVisibility(View.GONE);
                            myView.findViewById(R.id.rlFirst).setVisibility(View.VISIBLE);
                            myView.findViewById(R.id.llSecond).setVisibility(View.GONE);
                            myView.findViewById(R.id.llFirst).setVisibility(View.GONE);
                        }
                        if (column == Utils.Weather.ONE_COLUMN) {
                            // TODO: show No.4
                            //one widget one column
                            myView.findViewById(R.id.rlSecond).setVisibility(View.GONE);
                            myView.findViewById(R.id.rlFirst).setVisibility(View.GONE);
                            myView.findViewById(R.id.llSecond).setVisibility(View.GONE);
                            myView.findViewById(R.id.llFirst).setVisibility(View.VISIBLE);
                            myView.findViewById(R.id.widget2).setVisibility(View.INVISIBLE);
                            myView.findViewById(R.id.widget1).setVisibility(View.VISIBLE);
                        }
                        if (column == Utils.Weather.TWO_GRAPH) {
                            //add two graph here
                         /*   myView.findViewById(R.id.rlSecond).setVisibility(View.GONE);
                            myView.findViewById(R.id.rlFirst).setVisibility(View.VISIBLE);
                            myView.findViewById(R.id.llSecond).setVisibility(View.GONE);
                            myView.findViewById(R.id.llFirst).setVisibility(View.VISIBLE);
                            ((RelativeLayout) myView.findViewById(R.id.rlSecond)).removeAllViews();
                            ((RelativeLayout) myView.findViewById(R.id.rlFirst)).removeAllViews();
                            ((RelativeLayout) myView.findViewById(R.id.rlSecond)).addView(chart);
                            CombinedChart combinedChart = chart.findViewById(R.id.CombinedChart);
                            setUpChart(combinedChart, datachart, 2, list);
                            ((RelativeLayout) myView.findViewById(R.id.rlFirst)).addView(chart);
                            CombinedChart combinedChart2 = chart.findViewById(R.id.CombinedChart);
                            setUpChart(combinedChart2, datachart2, 2, list);*/

                        }
                        if (column == Utils.Weather.GRAPH_ABOVE_WEATHER) {
                            myView.findViewById(R.id.rlSecond).setVisibility(View.GONE);
                            myView.findViewById(R.id.rlFirst).setVisibility(View.VISIBLE);
                            myView.findViewById(R.id.llSecond).setVisibility(View.VISIBLE);
                            myView.findViewById(R.id.llFirst).setVisibility(View.GONE);
                            ((RelativeLayout) myView.findViewById(R.id.rlFirst)).removeAllViews();
                            ((RelativeLayout) myView.findViewById(R.id.rlFirst)).addView(chart);
                            CombinedChart combinedChart2 = chart.findViewById(R.id.CombinedChart);
                            for (Data ss : list) {
                                if (ss.getId() == datachart.getTemplatedId()) {
                                    ((TextView) chart.findViewById(R.id.tvNameGraph)).setText(ss.getName() + "");
                                    setUpChart(combinedChart2, datachart, 2, list, context, combinedChart2, ss);
                                }
                            }
                        }
                        if (column == Utils.Weather.GRAPH_ABOVE_TWO_WEATHER) {
                            myView.findViewById(R.id.rlSecond).setVisibility(View.GONE);
                            myView.findViewById(R.id.rlFirst).setVisibility(View.VISIBLE);
                            myView.findViewById(R.id.llSecond).setVisibility(View.VISIBLE);
                            myView.findViewById(R.id.llFirst).setVisibility(View.GONE);
                            ((RelativeLayout) myView.findViewById(R.id.rlFirst)).removeAllViews();
                            ((RelativeLayout) myView.findViewById(R.id.rlFirst)).addView(chart);
                            CombinedChart combinedChart2 = chart.findViewById(R.id.CombinedChart);
                            for (Data ss : list) {
                                if (ss.getId() == datachart.getTemplatedId()) {
                                    ((TextView) chart.findViewById(R.id.tvNameGraph)).setText(ss.getName() + "");
                                    setUpChart(combinedChart2, datachart, 2, list, context, combinedChart2, ss);
                                }
                            }

                        }

                    }
                }
                break;
            default:
                break;
        }
        return myView;
    }

    public static void setUpChart(CombinedChart chart, final graphData graphdata, int number, final List<Data> list, final Context context, final View viewParent, Data dataTemplate) {
        number = 2;
        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisRight().setDrawLabels(false);
        chart.setDrawValueAboveBar(false);
        chart.setDrawMarkers(true);
        chart.getXAxis().setTextSize(6f);
        chart.setBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setCenterAxisLabels(false);
        chart.getAxisRight().setDrawAxisLine(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.setNoDataText(App.getsInstance().getString(R.string.txt_no_weather_data));
        chart.setBackgroundColor(Color.WHITE);
        chart.setHighlightPerTapEnabled(true);

        switch (number) {
            case 1:
                chart.setDrawOrder(new CombinedChart.DrawOrder[]{
                        CombinedChart.DrawOrder.BAR
                });
                break;
            case 2:
                chart.setDrawOrder(new CombinedChart.DrawOrder[]{
                        CombinedChart.DrawOrder.LINE
                });
                break;
            case 3:
                chart.setDrawOrder(new CombinedChart.DrawOrder[]{
                        CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
                });
                break;
            default:
                break;
        }
 /*       Legend l = chart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);*/

      /*  YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)*/

      /*  YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)*/
        final List<graphData.yaxisResponse> listSave = graphdata.getYaxisResponseList();
        List<graphData.dataOfItemYaxis> longList = null;
        if (listSave != null && listSave.size() != 0) {
            longList = listSave.get(0).getDataOfItemYaxisList();
            for (graphData.yaxisResponse ss : listSave) {
                if (ss.getDataOfItemYaxisList().size() >= longList.size()) {
                    longList = ss.getDataOfItemYaxisList();
                }
            }
        }
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(false);
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        // hide legend
        Legend legend = chart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setFormSize(9f);
        legend.setTextSize(11f);
        legend.setXEntrySpace(4f);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter(new CustomXAxisDateFormatter(longList));
        chart.setDrawMarkers(true);
        MyMarkerView myMarkerView = new MyMarkerView(context, R.layout.layout_popup, list, listSave, graphdata);
        chart.setMarker(myMarkerView);
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });
        CombinedData data = new CombinedData();
        chart.setData(NumberOfBlock.addDataToChart(data, listSave, number, dataTemplate));
        //xAxis.setAxisMaximum(data.getXMax() + 0.5f);
        chart.invalidate();

    }


    public static String getUnit(EnumWeather weather) {
        switch (weather) {
            case WEATHER_TEMPERATURE:
                return "°C";
            case HUMIDITY:
                return "%";
            case WIND_DIRECTION:
                return "m/s";
            case PRECIPITATION:
                return "mm/h";
            case AMOUNT_OF_INSOLATION:
                return "wat/m2";
            default:
                return "";
        }
    }

    private static CombinedData addDataToChart(CombinedData data, List<graphData.yaxisResponse> graph, int number, Data dataTeplate) {
        List<GraphV3YAxis> graphV3YAxis = dataTeplate.getGraph().getGraphV3YAxis();
        switch (number) {
            case 1:
                //add only bar chart
                for (graphData.yaxisResponse ss : graph) {
                    String color = "";
                    for (GraphV3YAxis itemGraphV3YAxis1 : graphV3YAxis) {
                        if (ss.getYasixId() == itemGraphV3YAxis1.getId()) {
                            color = itemGraphV3YAxis1.getColor();
                        }
                    }
                    data.setData(generateBarData(ss.getDataOfItemYaxisList(), ss.getMeasureName() + " " + ss.getCalculationMethod(), color));
                }
                break;
            case 2:
                //add only line chart

                for (graphData.yaxisResponse ss : graph) {
                    String color = "";
                    for (GraphV3YAxis itemGraphV3YAxis1 : graphV3YAxis) {
                        if (ss.getYasixId() == itemGraphV3YAxis1.getId()) {
                            color = itemGraphV3YAxis1.getColor();
                        }
                    }
                    data.setData(generateLineData(ss.getDataOfItemYaxisList(), ss.getMeasureName() + " " + ss.getCalculationMethod(), color));
                }
                break;
            case 3:
                //add line and bar chart
                for (graphData.yaxisResponse ss : graph) {
                    String color = "";
                    for (GraphV3YAxis itemGraphV3YAxis1 : graphV3YAxis) {
                        if (ss.getYasixId() == itemGraphV3YAxis1.getId()) {
                            color = itemGraphV3YAxis1.getColor();
                        }
                    }
                    data.setData(generateLineData(ss.getDataOfItemYaxisList(), ss.getMeasureName() + " " + ss.getCalculationMethod(), color));
                }
                for (graphData.yaxisResponse ss : graph) {
                    String color = "";
                    for (GraphV3YAxis itemGraphV3YAxis1 : graphV3YAxis) {
                        if (ss.getYasixId() == itemGraphV3YAxis1.getId()) {
                            color = itemGraphV3YAxis1.getColor();
                        }
                    }
                    data.setData(generateBarData(ss.getDataOfItemYaxisList(), ss.getMeasureName() + " " + ss.getCalculationMethod(), color));
                }
                break;
            default:
                break;
        }
        data.setValueTypeface(Typeface.DEFAULT);
        return data;
    }

    private static BarData generateBarData(List<graphData.dataOfItemYaxis> graph, String name, String color) {
        ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
        if (graph != null) {
            for (int index = 0; index < graph.size(); index++) {
                entries1.add(new BarEntry(index, (float) graph.get(index).getY()));
            }
            BarDataSet set1 = new BarDataSet(entries1, name);
            set1.setColor(Color.parseColor(color));
            set1.setValues(entries1);
            set1.setDrawIcons(false);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData d = new BarData(dataSets);
            d.setValueTextSize(10f);
            d.setValueTypeface(Typeface.DEFAULT);
            d.setBarWidth(0.5f);
            return d;
        } else return null;
    }


    private static LineData generateLineData(List<graphData.dataOfItemYaxis> graph, String name, String color) {
        ArrayList<Entry> entries = new ArrayList<Entry>();
        if (!graph.isEmpty()) {
            for (int index = 0; index < graph.size(); index++) {
                entries.add(new Entry(index, (float) graph.get(index).getY()));
                Log.e(TAG, "list " + index + "=" + graph.get(index).getY());
            }
            LineDataSet set = new LineDataSet(entries, name);
            LineData d = new LineData(set);
            set.setColor(Color.parseColor(color));
            set.setLineWidth(2.5f);
            set.setCircleColor(Color.parseColor(color));
            set.setCircleRadius(5f);
            set.setFillColor(Color.parseColor(color));
            set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set.setDrawValues(false);
            set.setValueTextSize(10f);
            set.setAxisDependency(YAxis.AxisDependency.LEFT);
            d.addDataSet(set);
            return d;
        } else return null;
    }


}

