package com.mgbachi_ugo.chatbot.model;

import android.app.Activity;
import android.os.AsyncTask;

import com.mgbachi_ugo.chatbot.MainActivity;

import ai.api.AIServiceContext;
import ai.api.AIServiceException;
import ai.api.android.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

public class CommunicatorBot extends AsyncTask<AIRequest,Void, AIResponse> {

    Activity activity;
    AIDataService aiDataService;
    AIServiceContext aiServiceContext;

    public CommunicatorBot(Activity activity, AIDataService aiDataService, AIServiceContext aiServiceContext){
        this.activity = activity;
        this.aiDataService = aiDataService;
        this.aiServiceContext = aiServiceContext;
    }


    @Override
    protected AIResponse doInBackground(AIRequest... aiRequests) {
        AIRequest request = aiRequests[0];
        try {
            return aiDataService.request(request, aiServiceContext);
        } catch (AIServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(AIResponse aiResponse) {
        ((MainActivity)activity).callback(aiResponse);
    }
}
