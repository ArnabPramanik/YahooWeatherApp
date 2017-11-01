package com.arnab.android.myyahooweather;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by arnab on 10/27/17.
 */

class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {

//  COMPLETED (14) Remove the mWeatherData declaration

//  COMPLETED (1) Declare a private final Context field called mContext
    /* The context we use to utility methods, app resources and layout inflaters */
private final Context mContext;

    /*
     * Below, we've defined an interface to handle clicks on items within this Adapter. In the
     * constructor of our ForecastAdapter, we receive an instance of a class that has implemented
     * said interface. We store that instance in this variable to call the onClick method whenever
     * an item is clicked in the list.
     */
final private ForecastAdapterOnClickHandler mClickHandler;

/**
 * The interface that receives onClick messages.
 */
public interface ForecastAdapterOnClickHandler {
    void onClick(String weatherForDay);
}

    //  COMPLETED (2) Declare a private Cursor field called mCursor
    private Cursor mCursor;

//  COMPLETED (3) Add a Context field to the constructor and store that context in mContext
    /**
     * Creates a ForecastAdapter.
     *
     * @param context Used to talk to the UI and app resources
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public ForecastAdapter(@NonNull Context context, ForecastAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (like ours does) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new ForecastAdapterViewHolder that holds the View for each list item
     */
    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.recycle_list_item, viewGroup, false);

        view.setFocusable(true);

        return new ForecastAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param forecastAdapterViewHolder The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ForecastAdapterViewHolder forecastAdapterViewHolder, int position) {
        mCursor.moveToPosition(position);


        /*******************
         * Weather Summary *
         *******************/
//      COMPLETED (7) Generate a weather summary with the date, description, high and low
        /* Read date from the cursor */
        String date = mCursor.getString(MainActivity.INDEX_WEATHER_DATE);

        String code = mCursor.getString(MainActivity.INDEX_WEATHER_CODE);

        String high = mCursor.getString(MainActivity.INDEX_WEATHER_HIGH);
        /* Read low temperature from the cursor (in degrees celsius) */
        String low = mCursor.getString(MainActivity.INDEX_WEATHER_LOW);


        String weatherSummary = date + " - " + high + " - " + low;

//      COMPLETED (8) Display the summary that you created above
        forecastAdapterViewHolder.weatherSummary.setText(weatherSummary);
    }
    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
//      COMPLETED (9) Delete the current body of getItemCount
//      COMPLETED (10) If mCursor is null, return 0. Otherwise, return the count of mCursor
        if (null == mCursor) {return 0;}
        return mCursor.getCount();
    }

//  COMPLETED (11) Create a new method that allows you to swap Cursors.
    /**
     * Swaps the cursor used by the ForecastAdapter for its weather data. This method is called by
     * MainActivity after a load has finished, as well as when the Loader responsible for loading
     * the weather data is reset. When this method is called, we assume we have a completely new
     * set of data, so we call notifyDataSetChanged to tell the RecyclerView to update.
     *
     * @param newCursor the new cursor to use as ForecastAdapter's data source
     */
    void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
//      COMPLETED (12) After the new Cursor is set, call notifyDataSetChanged
        notifyDataSetChanged();
    }

/**
 * A ViewHolder is a required part of the pattern for RecyclerViews. It mostly behaves as
 * a cache of the child views for a forecast item. It's also a convenient place to set an
 * OnClickListener, since it has access to the adapter and the views.
 */
class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    final TextView weatherSummary;

    ForecastAdapterViewHolder(View view) {
        super(view);

        weatherSummary = (TextView) view.findViewById(R.id.weatherView);

        view.setOnClickListener(this);
    }

    /**
     * This gets called by the child views during a click. We fetch the date that has been
     * selected, and then call the onClick handler registered with this adapter, passing that
     * date.
     *
     * @param v the View that was clicked
     */
    @Override
    public void onClick(View v) {
        //  COMPLETED (13) Instead of passing the String from the data array, use the weatherSummary text!
        String weatherForDay = weatherSummary.getText().toString();
        mClickHandler.onClick(weatherForDay);
    }
}
}