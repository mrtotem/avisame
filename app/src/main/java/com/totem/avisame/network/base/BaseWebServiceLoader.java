package com.totem.avisame.network.base;

import android.content.Context;
import android.support.v4.content.Loader;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.totem.avisame.network.ServiceCatalog;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseWebServiceLoader<T> extends Loader<LoaderResponse<T>> {

    protected boolean mAPIOnly = false;
    protected boolean mUseCache = false;
    // We hold a reference to the Loader’s data here.
    protected LoaderResponse<T> mData;

    public BaseWebServiceLoader(Context ctx) {
        // Loaders may be used across multiple Activities (assuming they aren't
        // bound to the LoaderManager), so NEVER hold a reference to the context
        // directly. Doing so will cause you to leak an entire Activity's context.
        // The superclass constructor will store a reference to the Application
        // Context instead, and can be retrieved with a call to getContext().
        super(ctx);
    }

    public final void setUseCache(boolean mUseCache) {
        this.mUseCache = mUseCache;
    }

    public final void setAPIOnly(boolean mAPIOnly) {
        this.mAPIOnly = mAPIOnly;
    }

    /********************************************************/
    /** (2) Deliver the results to the registered listener **/
    /********************************************************/

    @Override
    public void deliverResult(LoaderResponse<T> data) {

        if (isAbandoned()) {
            //Hold data but do not report any new update.
            return;
        }

        if (isReset()) {
            // The Loader has been reset; ignore the result and invalidate the data.
            releaseResources(data);
            return;
        }

        // Hold a reference to the old data so it doesn't get garbage collected.
        // We must protect it until the new data has been delivered.
        LoaderResponse<T> oldData = mData;
        mData = data;

        if (isStarted()) {
            // If the Loader is in a started state, deliver the results to the
            // client. The superclass method does this for us.
            super.deliverResult(data);
        }

        // Invalidate the old data as we don't need it any more.
        if (oldData != null && oldData != data) {
            releaseResources(oldData);
        }
    }

    /*********************************************************/
    /** (3) Implement the Loader’s state-dependent behavior **/
    /*********************************************************/

    @Override
    protected void onForceLoad() {
        onStartLoading();
    }

    @Override
    protected void onStartLoading() {

        if (mData != null) {
            // Deliver any previously loaded data immediately.
            deliverResult(mData);
        }

        startServiceCall();
    }

    @Override
    protected void onStopLoading() {
        // The Loader is in a stopped state, so we should attempt to cancel the
        // current load (if there is one).
        cancelLoad();
    }

    @Override
    protected void onReset() {
        // Ensure the loader has been stopped.
        onStopLoading();

        // At this point we can release the resources associated with 'mData'.
        if (mData != null) {
            releaseResources(mData);
            mData = null;
        }
    }

    protected String getBaseUrl() {
        return ServiceCatalog.BASE_API_URL;
    }

    /**
     * Use this to free any data related stuff needed.
     *
     * @param data
     */
    protected void releaseResources(LoaderResponse<T> data) {
        // For a simple List, there is nothing to do. For something like a Cursor, we
        // would close it in this method. All resources associated with the Loader
        // should be released here.
    }

    protected LoaderResponse<T> wrapResponse(T response) {
        return new LoaderResponse<>(response);
    }

    protected LoaderResponse<T> wrapError(Error error) {
        return new LoaderResponse<>(error);
    }

    protected abstract void startServiceCall();

    public abstract boolean cancelLoad();

    protected abstract HttpOperations getHTTPMethod();

    protected abstract String getPath();

    Response.ErrorListener getErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String data;
                NetworkResponse response = error.networkResponse;
                if (response != null) {

                    switch (response.statusCode) {
                        case 401:
                            onInvalidToken();
                            break;
                        case 403:
                            onInvalidToken();
                            break;
                        case 503:
                            deliverResult(wrapError(new Error("Error del servidor")));
                            break;
                        case 500:
                            deliverResult(wrapError(new Error("Error del servidor")));
                            break;
                        default:
                            data = new String(response.data);
                            data = trimMessage(data, "message");
                            deliverResult(wrapError(new Error(data)));
                            break;
                    }
                } else {
                    deliverResult(wrapError(new Error("Error desconocido")));
                }
            }
        };
    }

    public String trimMessage(String json, String key) {
        String trimmedString = null;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

    /**
     * Checks for server error in a valid JSON
     *
     * @param response
     * @return
     */
    protected Error checkForError(JSONObject response) {

//        {"result":{"error":"Not Found","snippet":"visit"}}
        Error error = null;
        try {
            String responseObject;
            if (response != null && response.has("code") && response.has("message")) {
                responseObject = response.getString("message");
                error = new Error(responseObject);
            }
        } catch (JSONException e) {
            Log.e("checkForError:", e.getLocalizedMessage());
            error = new Error("Unknown error");
        }
        return error;
    }

    protected abstract void onInvalidToken();
}