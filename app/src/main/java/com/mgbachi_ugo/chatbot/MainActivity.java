package com.mgbachi_ugo.chatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mgbachi_ugo.chatbot.adapter.RecyclerViewAdapter;
import com.mgbachi_ugo.chatbot.model.CommunicatorBot;
import com.mgbachi_ugo.chatbot.model.MessageMutator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ai.api.AIServiceContext;
import ai.api.AIServiceContextBuilder;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

public class MainActivity extends AppCompatActivity {

    List<Integer> viewTypeList;
    List<MessageMutator> messageList;
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    EditText typedMessage;
    AIDataService aiDataService;
    AIRequest aiRequest;
    AIServiceContext aiServiceContext;
    String uniqueId;
    CommunicatorBot communicatorBot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewLayout);
        typedMessage = findViewById(R.id.typedMessage);

        setBaseView();
        initializeBot();

        final Button send_btn = findViewById(R.id.sendButton);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = typedMessage.getText().toString().toLowerCase();
                if(TextUtils.isEmpty(message)) {
                    typedMessage.setError("Please type something to send");
                    return;
                }else{
                    recyclerView.getLayoutManager().scrollToPosition(recyclerViewAdapter.getItemCount());
                    typedMessage.setText("");
                    viewTypeList.add(RecyclerViewAdapter.SENT);
                    MessageMutator messageMutator = new MessageMutator();
                    messageMutator.setMessage(message);
                    messageList.add(messageMutator);
                    recyclerViewAdapter.notifyDataSetChanged();
                    sendMessage(message);
                }
            }
        });

    }

    private void initializeBot() {
        final AIConfiguration configuration = new AIConfiguration("291b3eb5ebb24fc791ca55705f6c33d9",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        uniqueId = UUID.randomUUID().toString();
        aiDataService = new AIDataService(this,configuration);
        aiServiceContext = AIServiceContextBuilder.buildFromSessionId(uniqueId);
        aiRequest = new AIRequest();
    }

    private void setBaseView() {
        viewTypeList = new ArrayList<>();
        viewTypeList.add(RecyclerViewAdapter.RECEIVED);
        messageList = new ArrayList<>();
        MessageMutator messageMutator = new MessageMutator();
        messageMutator.setMessage("Hi! there");
        messageList.add(messageMutator);
        recyclerViewAdapter = new RecyclerViewAdapter(viewTypeList,messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);
    }
    public void sendMessage(String message) {
        aiRequest.setQuery(message);
        communicatorBot = new CommunicatorBot(MainActivity.this,aiDataService,aiServiceContext);
        communicatorBot.execute(aiRequest);
    }

    public void callback(AIResponse aiResponse) {
        if(aiResponse != null){
            String botReply = aiResponse.getResult().getFulfillment().getSpeech();
            recyclerView.getLayoutManager().scrollToPosition(recyclerViewAdapter.getItemCount());
            typedMessage.setText("");
            viewTypeList.add(RecyclerViewAdapter.RECEIVED);
            MessageMutator messageMutator = new MessageMutator();
            messageMutator.setMessage(botReply);
            messageList.add(messageMutator);
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }

}