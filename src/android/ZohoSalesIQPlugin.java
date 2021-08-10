package com.zohosalesiq.mobilisten;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.zoho.commons.ChatComponent;
import com.zoho.commons.OnInitCompleteListener;
import com.zoho.livechat.android.SIQDepartment;
import com.zoho.livechat.android.SIQVisitor;
import com.zoho.livechat.android.SIQVisitorLocation;
import com.zoho.livechat.android.SalesIQCustomAction;
import com.zoho.livechat.android.VisitorChat;
import com.zoho.livechat.android.ZohoLiveChat;
import com.zoho.livechat.android.constants.ConversationType;
import com.zoho.livechat.android.constants.SalesIQConstants;
import com.zoho.livechat.android.exception.InvalidEmailException;
import com.zoho.livechat.android.exception.InvalidVisitorIDException;
import com.zoho.livechat.android.listeners.ConversationListener;
import com.zoho.livechat.android.listeners.DepartmentListener;
import com.zoho.livechat.android.listeners.FAQCategoryListener;
import com.zoho.livechat.android.listeners.FAQListener;
import com.zoho.livechat.android.listeners.OpenArticleListener;
import com.zoho.livechat.android.listeners.OperatorImageListener;
import com.zoho.livechat.android.listeners.SalesIQActionListener;
import com.zoho.livechat.android.listeners.SalesIQChatListener;
import com.zoho.livechat.android.listeners.SalesIQCustomActionListener;
import com.zoho.livechat.android.listeners.SalesIQFAQListener;
import com.zoho.livechat.android.listeners.SalesIQListener;
import com.zoho.livechat.android.models.SalesIQArticle;
import com.zoho.livechat.android.models.SalesIQArticleCategory;
import com.zoho.livechat.android.utils.LiveChatUtil;
import com.zoho.salesiqembed.ZohoSalesIQ;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

public class ZohoSalesIQPlugin extends CordovaPlugin{

    private static String fcmtoken = null;
    private static Boolean istestdevice = true;

    private static final String SUPPORT_OPENED = "SUPPORT_OPENED";         // No I18N
    private static final String SUPPORT_CLOSED = "SUPPORT_CLOSED";         // No I18N
    private static final String CHATVIEW_OPENED = "CHATVIEW_OPENED";         // No I18N
    private static final String CHATVIEW_CLOSED = "CHATVIEW_CLOSED";         // No I18N
    private static final String CHAT_ATTENDED = "CHAT_ATTENDED";         // No I18N
    private static final String CHAT_MISSED = "CHAT_MISSED";         // No I18N
    private static final String CHAT_OPENED = "CHAT_OPENED";         // No I18N
    private static final String CHAT_CLOSED = "CHAT_CLOSED";         // No I18N
    private static final String CHAT_REOPENED = "CHAT_REOPENED";         // No I18N
    private static final String ARTICLE_LIKED = "ARTICLE_LIKED";         // No I18N
    private static final String ARTICLE_DISLIKED = "ARTICLE_DISLIKED";         // No I18N
    private static final String ARTICLE_OPENED = "ARTICLE_OPENED";         // No I18N
    private static final String ARTICLE_CLOSED = "ARTICLE_CLOSED";         // No I18N
    private static final String OPERATORS_ONLINE = "OPERATORS_ONLINE";         // No I18N
    private static final String OPERATORS_OFFLINE = "OPERATORS_OFFLINE";         // No I18N
    private static final String VISITOR_IPBLOCKED = "VISITOR_IPBLOCKED";         // No I18N
    private static final String FEEDBACK_RECEIVED = "FEEDBACK_RECEIVED";         // No I18N
    private static final String RATING_RECEIVED = "RATING_RECEIVED";         // No I18N
    private static final String PERFORM_CHATACTION = "PERFORM_CHATACTION";         // No I18N
    private static final String CUSTOMTRIGGER = "CUSTOMTRIGGER";         // No I18N
    private static final String CHAT_QUEUE_POSITION_CHANGED = "CHAT_QUEUE_POSITION_CHANGED";         // No I18N

    private static final String TYPE_OPEN = "OPEN";         // No I18N
    private static final String TYPE_CONNECTED = "CONNECTED";         // No I18N
    private static final String TYPE_CLOSED = "CLOSED";         // No I18N
    private static final String TYPE_ENDED = "ENDED";         // No I18N
    private static final String TYPE_MISSED = "MISSED";         // No I18N
    private static final String TYPE_WAITING = "WAITING";         // No I18N

    private static final int INVALID_FILTER_CODE = 604;
    private static final String INVALID_FILTER_TYPE = "invalid filter type";         // No I18N
    private static final String TRYCATCH_EXCEPTION = "trycatch exception";         // No I18N

  private static Hashtable<String, SalesIQCustomActionListener> actionsList = new Hashtable<>();

  @Override
  public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
      if (action != null) {
          if (action.equalsIgnoreCase("init")){
              this.init(data.get(0).toString(),data.get(1).toString(), callbackContext);
          }
          if (action.equals("enableScreenshotOption")) {
              this.enableScreenshotOption();
          }
          if (action.equals("disableScreenshotOption")) {
              this.disableScreenshotOption();
          }
          if (action.equals("enablePreChatForms")) {
              this.enablePreChatForms();
          }
          if (action.equals("disablePreChatForms")) {
              this.disablePreChatForms();
          }
          if (action.equals("setVisitorNameVisibility")) {
              this.setVisitorNameVisibility((boolean)data.get(0));
          }
          if (action.equals("setChatTitle")) {
              this.setChatTitle(data.get(0).toString());
          }
          if (action.equals("setLanguage")) {
              this.setLanguage(data.get(0).toString());
          }
          if (action.equals("setDepartment")) {
              this.setDepartment(data.get(0).toString());
          }
          if (action.equals("setDepartments")) {
              this.setDepartments((JSONArray) data.get(0));
          }
          if (action.equals("setOperatorEmail")) {
              this.setOperatorEmail(data.get(0).toString());
          }
          if (action.equals("showOperatorImageInChat")) {
              this.showOperatorImageInChat((boolean)data.get(0));
          }
          if (action.equals("setFeedbackVisibility")) {
              this.setFeedbackVisibility((boolean)data.get(0));
          }
          if (action.equals("setRatingVisibility")) {
              this.setRatingVisibility((boolean)data.get(0));
          }
          if (action.equals("showOperatorImageInLauncher")) {
              this.showOperatorImageInLauncher((boolean)data.get(0));
          }
          if (action.equals("show")) {
              this.show();
          }
          if (action.equals("openChatWithID")) {
              this.openChatWithID(data.get(0).toString());
          }
          if (action.equals("openNewChat")) {
              this.openNewChat();
          }
          if (action.equals("showOfflineMessage")) {
              this.showOfflineMessage((boolean)data.get(0));
          }
          if (action.equals("endChat")) {
              this.endChat(data.get(0).toString());
          }
          if (action.equals("showLauncher")) {
              this.showLauncher((boolean)data.get(0));
          }
          if (action.equals("setVisitorName")) {
              this.setVisitorName(data.get(0).toString());
          }
          if (action.equals("setVisitorEmail")) {
              this.setVisitorEmail(data.get(0).toString());
          }
          if (action.equals("setVisitorContactNumber")) {
              this.setVisitorContactNumber(data.get(0).toString());
          }
          if (action.equals("setVisitorAddInfo")) {
              this.setVisitorAddInfo(data.get(0).toString(),data.get(1).toString());
          }
          if (action.equals("setQuestion")) {
              this.setQuestion(data.get(0).toString());
          }
          if (action.equals("startChat")) {
              this.startChat(data.get(0).toString());
          }
          if (action.equals("setConversationVisibility")) {
              this.setConversationVisibility((boolean)data.get(0));
          }
          if (action.equals("setConversationListTitle")) {
              this.setConversationListTitle(data.get(0).toString());
          }
          if (action.equals("setFAQVisibility")) {
              this.setFAQVisibility((boolean)data.get(0));
          }
          if (action.equals("registerVisitor")) {
              this.registerVisitor(data.get(0).toString());
          }
          if (action.equals("unregisterVisitor")) {
              this.unregisterVisitor();
          }
          if (action.equals("setPageTitle")) {
              this.setPageTitle(data.get(0).toString());
          }
          if (action.equals("setCustomAction")) {
              this.setCustomAction(data.get(0).toString());
          }
          if (action.equals("performCustomAction")) {
              this.performCustomAction(data.get(0).toString());
          }
          if (action.equals("enableInAppNotification")) {
              this.enableInAppNotification();
          }
          if (action.equals("disableInAppNotification")) {
              this.disableInAppNotification();
          }
          if (action.equals("setThemeColorforiOS")) {
              this.setThemeColorforiOS(data.get(0).toString());
          }
          if (action.equals("fetchAttenderImage")) {
              cordova.getThreadPool().execute(new Runnable() {
                  public void run() {
                      try {
                          fetchAttenderImage(LiveChatUtil.getString(data.get(0)),LiveChatUtil.getBoolean(data.get(1)),callbackContext);
                      } catch (JSONException e) {
                          LiveChatUtil.log(e);
                      }
                  }
              });
          }
          if (action.equals("getChats")) {
              this.getChats(callbackContext);
          }
          if (action.equals("getChatsWithFilter")) {
              this.getChatsWithFilter(LiveChatUtil.getString(data.get(0)),callbackContext);
          }
          if (action.equals("getDepartments")) {
              this.getDepartments(callbackContext);
          }
          if (action.equals("getArticles")) {
              this.getArticles(callbackContext);
          }
          if (action.equals("getArticlesWithCategoryID")) {
              this.getArticlesWithCategoryID(LiveChatUtil.getString(data.get(0)),callbackContext);
          }
          if (action.equals("getCategories")) {
              this.getCategories(callbackContext);
          }
          if (action.equals("openArticle")) {
              this.openArticle(LiveChatUtil.getString(data.get(0)),callbackContext);
          }
          if (action.equals("registerChatAction")) {
              this.registerChatAction(data.get(0).toString());
          }
          if (action.equals("unregisterChatAction")) {
              this.unregisterChatAction(data.get(0).toString());
          }
          if (action.equals("unregisterAllChatActions")) {
              this.unregisterAllChatActions();
          }
          if (action.equals("setChatActionTimeout")) {
              this.setChatActionTimeout(LiveChatUtil.getDouble(data.get(0)));
          }
          if (action.equals("completeChatAction")) {
              this.completeChatAction(data.get(0).toString());
          }
          if (action.equals("completeChatActionWithMessage")) {
              this.completeChatActionWithMessage(data.get(0).toString(),(boolean)data.get(1),data.get(2).toString());
          }
          if (action.equals("setVisitorLocation")) {
              this.setVisitorLocation((JSONObject)data.get(0));
          }
          if (action.equals("syncThemeWithOS")) {
            this.syncThemeWithOS((boolean)data.get(0));
          }
          if (action.equals("isMultipleOpenChatRestricted")) {
            this.isMultipleOpenChatRestricted(callbackContext);
          }
          return true;
      }
      return false;
  }

  private void init(final String appKey, final String accessKey, final CallbackContext callbackContext){
      final Application context = this.cordova.getActivity().getApplication();
      final Activity activity = this.cordova.getActivity();
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              initSalesIQ(context, activity, appKey, accessKey, callbackContext);
              ZohoSalesIQ.setListener(new ZohoSalesIQPluginListener());
              ZohoSalesIQ.Chat.setListener(new ZohoSalesIQPluginListener());
              ZohoSalesIQ.FAQ.setListener(new ZohoSalesIQPluginListener());
              ZohoSalesIQ.ChatActions.setListener(new ZohoSalesIQPluginListener());
          }
      });
  }

  private void enableScreenshotOption(){
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Chat.setVisibility(ChatComponent.screenshot, true);
          }
      });
  }

  private void disableScreenshotOption(){
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Chat.setVisibility(ChatComponent.screenshot, false);
          }
      });
  }

  private void enablePreChatForms(){
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Chat.setVisibility(ChatComponent.prechatForm, true);
          }
      });
  }

  private void disablePreChatForms(){
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Chat.setVisibility(ChatComponent.prechatForm, false);
          }
      });
  }

  private void setVisitorNameVisibility(final boolean visibility){
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Chat.setVisibility(ChatComponent.visitorName, visibility);
          }
      });
  }

  private void setChatTitle(final String title) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Chat.setTitle(title);
          }
      });
  }

  private void setLanguage(final String code) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Chat.setLanguage(code);
          }
      });
  }

  private void setDepartment(final String department) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Chat.setDepartment(department);
          }
      });
  }

  private void setDepartments(final JSONArray department) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ArrayList<String> departmentList = new ArrayList<String>();
              if (department != null) {
                  try {
                      for (int i = 0; i < department.length(); i++) {
                          departmentList.add(department.getString(i));
                      }
                      ZohoSalesIQ.Chat.setDepartments(departmentList);
                  }
                  catch (JSONException e) {
                      LiveChatUtil.log(e);
                  }
              }
          }
      });
  }

  private void setOperatorEmail(final String email) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              try {
                  ZohoSalesIQ.Chat.setOperatorEmail(email);
              } catch (InvalidEmailException e) {
                  LiveChatUtil.log(e);
              }
          }
      });
  }

  private void showOperatorImageInChat(final Boolean visible) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Chat.setVisibility(ChatComponent.operatorImage, visible);
          }
      });
  }

  private void setFeedbackVisibility(final Boolean visible) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Chat.setVisibility(ChatComponent.feedback, visible);
          }
      });
  }

  private void setRatingVisibility(final Boolean visible) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Chat.setVisibility(ChatComponent.rating, visible);
          }
      });
  }

  private void showOperatorImageInLauncher(final Boolean show) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              Activity activity = cordova.getActivity();
              if (activity != null) {
                  ZohoSalesIQ.getApplicationManager().setCurrentActivity(activity);
                  ZohoSalesIQ.Chat.showOperatorImageInLauncher(show);
              }
          }
      });
  }

  private void show() {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Chat.show();
          }
      });
  }

  private void openChatWithID(final String chat_id) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Chat.open(chat_id);
          }
      });
  }

  private void openNewChat() {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Chat.openNewChat();
          }
      });
  }

  private void showOfflineMessage(final Boolean show) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Chat.showOfflineMessage(show);
          }
      });
  }

  private void endChat(final String chatID) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Chat.endChat(chatID);
          }
      });
  }

  private void showLauncher(Boolean visible) {
      ZohoSalesIQ.showLauncher(visible);
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              Activity activity = cordova.getActivity();
              if (activity != null) {
                  ZohoSalesIQ.getApplicationManager().setCurrentActivity(activity);
                  ZohoSalesIQ.getApplicationManager().refreshChatBubble();
              }
          }
      });
  }

  private void setVisitorName(final String name) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Visitor.setName(name);
          }
      });
  }

  private void setVisitorEmail(final String email) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Visitor.setEmail(email);
          }
      });
  }

  private void setVisitorContactNumber(final String number) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Visitor.setContactNumber(number);
          }
      });
  }

  private void setVisitorAddInfo(final String key, final String value) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Visitor.addInfo(key, value);
          }
      });
  }

  private void setQuestion(final String question) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Visitor.setQuestion(question);
          }
      });
  }

  private void startChat(final String question) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Visitor.startChat(question);
          }
      });
  }

  private void setConversationVisibility(final Boolean visible) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Conversation.setVisibility(visible);
          }
      });
  }

  private void setConversationListTitle(final String title) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Conversation.setTitle(title);
          }
      });
  }

  private void setFAQVisibility(final Boolean visible) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.FAQ.setVisibility(visible);
          }
      });
  }

  private void registerVisitor(final String uniqueid){
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              try {
                  ZohoSalesIQ.registerVisitor(uniqueid);
              } catch (InvalidVisitorIDException e) {
                  LiveChatUtil.log(e);
              }
          }
      });
  }

  private void unregisterVisitor(){
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              Activity activity = cordova.getActivity();
              ZohoSalesIQ.unregisterVisitor(activity);
          }
      });
  }

  private void setPageTitle(final String title) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Tracking.setPageTitle(title);
          }
      });
  }

  private void setCustomAction(final String actionName) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Tracking.setCustomAction(actionName);
          }
      });
  }

  private void performCustomAction(final String actionName) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Tracking.setCustomAction(actionName);
          }
      });
  }

  private void enableInAppNotification() {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Notification.enableInApp();
          }
      });
  }

  private void disableInAppNotification() {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.Notification.disableInApp();
          }
      });
  }

  private void setThemeColorforiOS(String colorCode) {

  }

  private void fetchAttenderImage(String attenderId, Boolean defaultImage, final CallbackContext imageCallback) {
      ZohoSalesIQ.Chat.fetchAttenderImage(attenderId, defaultImage, new OperatorImageListener() {
          @Override
          public void onSuccess(Drawable drawable) {
              if (drawable != null) {
                  Bitmap bitmap;

                  try {
                      bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

                      Canvas canvas = new Canvas(bitmap);
                      drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                      drawable.draw(canvas);

                      ByteArrayOutputStream baos = new ByteArrayOutputStream();
                      bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                      byte[] byteArrayImage = baos.toByteArray();

                      String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
                      encodedImage = encodedImage.replace("\n", "");         // No I18N

                      imageCallback.success(encodedImage);
                  } catch (OutOfMemoryError e) {
                      imageCallback.error(e.getMessage());
                  }
              } else {
                  imageCallback.success("");
              }
          }

          @Override
          public void onFailure(int code, String message) {
              HashMap errorMap = new HashMap();
              errorMap.put("code", code);         // No I18N
              errorMap.put("message", message);         // No I18N
              JSONObject errorObject = new JSONObject(errorMap);
              imageCallback.error(errorObject);
          }
      });
  }

    private void getChats(final CallbackContext listCallback){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable(){
            public void run(){
                ZohoSalesIQ.Chat.getList(new ConversationListener(){
                    @Override
                    public void onSuccess(ArrayList<VisitorChat> arrayList){
                        if (arrayList != null){
                            ArrayList<HashMap> array = new ArrayList<>();
                            for (int i=0; i<arrayList.size(); i++){
                                VisitorChat chat = arrayList.get(i);
                                HashMap visitorMap = getChatMapObject(chat);
                                array.add(visitorMap);
                            }
                            JSONArray jsonArray = new JSONArray(array);
                            listCallback.success(jsonArray);
                        }
                    }

                    @Override
                    public void onFailure(int code, String message){
                        HashMap errorMap = new HashMap();
                        errorMap.put("code", code);         // No I18N
                        errorMap.put("message", message);         // No I18N
                        JSONObject errorObject = new JSONObject(errorMap);
                        listCallback.error(errorObject);
                    }
                });
            }
        });
    }

    private void getChatsWithFilter(final String filter, final CallbackContext listCallback) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                try {
                    if (isValidFilterName(filter)) {
                        ConversationType filterName = getFilterName(filter);
                        ZohoSalesIQ.Chat.getList(filterName, new ConversationListener() {
                            @Override
                            public void onSuccess(ArrayList<VisitorChat> arrayList) {
                                if (arrayList != null){
                                    ArrayList<HashMap> array = new ArrayList<>();
                                    for (int i=0; i<arrayList.size(); i++){
                                        VisitorChat chat = arrayList.get(i);
                                        HashMap visitorMap = getChatMapObject(chat);
                                        array.add(visitorMap);
                                    }
                                    JSONArray jsonArray = new JSONArray(array);
                                    listCallback.success(jsonArray);
                                }
                            }

                            @Override
                            public void onFailure(int code, String message) {
                                HashMap errorMap = new HashMap();
                                errorMap.put("code", code);         // No I18N
                                errorMap.put("message", message);         // No I18N
                                JSONObject errorObject = new JSONObject(errorMap);
                                listCallback.error(errorObject);
                            }
                        });
                    }
                    else{
                        HashMap errorMap = new HashMap();
                        errorMap.put("code", INVALID_FILTER_CODE);         // No I18N
                        errorMap.put("message", INVALID_FILTER_TYPE);         // No I18N
                        JSONObject errorObject = new JSONObject(errorMap);
                        listCallback.error(errorObject);
                    }
                }
                catch (Exception e){
                    LiveChatUtil.log(e);
                }
            }
        });
    }

  private void getDepartments(final CallbackContext departmentCallback) {
    Handler handler = new Handler(Looper.getMainLooper());
    handler.post(new Runnable() {
      public void run() {
        ZohoLiveChat.Chat.getDepartments(new DepartmentListener() {
          @Override
          public void onSuccess(ArrayList<SIQDepartment> departmentsList) {
            if (departmentsList != null){
              ArrayList<HashMap> array = new ArrayList<>();
              for (int i=0; i<departmentsList.size(); i++){
                SIQDepartment department = departmentsList.get(i);
                HashMap departmentMap = getDepartmentMapObject(department);
                array.add(departmentMap);
              }
              JSONArray jsonArray = new JSONArray(array);
              departmentCallback.success(jsonArray);
            }
          }

          @Override
          public void onFailure(int code, String message) {
            HashMap errorMap = new HashMap();
            errorMap.put("code", code);         // No I18N
            errorMap.put("message", message);         // No I18N
            JSONObject errorObject = new JSONObject(errorMap);
            departmentCallback.error(errorObject);
          }
        });
      }
    });
  }

    private void getArticles(final CallbackContext articlesCallback) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                ZohoSalesIQ.FAQ.getArticles(new FAQListener() {
                    @Override
                    public void onSuccess(ArrayList<SalesIQArticle> articlesList) {
                        if (articlesList != null) {
                            ArrayList<HashMap> array = new ArrayList<>();
                            for (int i=0; i<articlesList.size(); i++){
                                SalesIQArticle article = articlesList.get(i);
                                HashMap articleMap = getArticleMapObject(article);
                                array.add(articleMap);
                            }
                            JSONArray jsonArray = new JSONArray(array);
                            articlesCallback.success(jsonArray);
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        HashMap errorMap = new HashMap();
                        errorMap.put("code", code);         // No I18N
                        errorMap.put("message", message);         // No I18N
                        JSONObject errorObject = new JSONObject(errorMap);
                        articlesCallback.error(errorObject);
                    }
                });
            }
        });
    }

    private void getArticlesWithCategoryID(final String categoryId, final CallbackContext articlesCallback) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                ZohoSalesIQ.FAQ.getArticles(categoryId, new FAQListener() {
                    @Override
                    public void onSuccess(ArrayList<SalesIQArticle> articlesList) {
                        if (articlesList != null) {
                            ArrayList<HashMap> array = new ArrayList<>();
                            for (int i=0; i<articlesList.size(); i++){
                                SalesIQArticle article = articlesList.get(i);
                                HashMap articleMap = getArticleMapObject(article);
                                array.add(articleMap);
                            }
                            JSONArray jsonArray = new JSONArray(array);
                            articlesCallback.success(jsonArray);
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        HashMap errorMap = new HashMap();
                        errorMap.put("code", code);         // No I18N
                        errorMap.put("message", message);         // No I18N
                        JSONObject errorObject = new JSONObject(errorMap);
                        articlesCallback.error(errorObject);
                    }
                });
            }
        });
    }

    private void getCategories(final CallbackContext categoryCallback) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                ZohoSalesIQ.FAQ.getCategories(new FAQCategoryListener() {
                    @Override
                    public void onSuccess(ArrayList<SalesIQArticleCategory> categoryList) {
                        if (categoryList != null) {
                            ArrayList<HashMap> array = new ArrayList<>();

                            for (int i=0; i<categoryList.size(); i++){
                                SalesIQArticleCategory category = categoryList.get(i);

                                HashMap categoryMap = new HashMap();
                                categoryMap.put("id", category.getCategoryid());         // No I18N
                                categoryMap.put("name", category.getCategoryname());         // No I18N
                                categoryMap.put("articleCount", category.getCount());         // No I18N
                                array.add(categoryMap);
                            }
                            JSONArray jsonArray = new JSONArray(array);
                            categoryCallback.success(jsonArray);
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        HashMap errorMap = new HashMap();
                        errorMap.put("code", code);         // No I18N
                        errorMap.put("message", message);         // No I18N
                        JSONObject errorObject = new JSONObject(errorMap);
                        categoryCallback.error(errorObject);
                    }
                });
            }
        });
    }

    private void openArticle(final String id, final CallbackContext articlesCallback) {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                ZohoSalesIQ.FAQ.openArticle(id, new OpenArticleListener() {
                    @Override
                    public void onSuccess() {
                        articlesCallback.success();
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        HashMap errorMap = new HashMap();
                        errorMap.put("code", code);         // No I18N
                        errorMap.put("message", message);         // No I18N
                        JSONObject errorObject = new JSONObject(errorMap);
                        articlesCallback.error(errorObject);
                    }
                });
            }
        });
    }

  private void registerChatAction(final String actionName) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.ChatActions.register(actionName);
          }
      });
  }

  private void unregisterChatAction(final String actionName) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.ChatActions.unregister(actionName);
          }
      });
  }

  private void unregisterAllChatActions() {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.ChatActions.unregisterAll();
          }
      });
  }

  private void setChatActionTimeout(final double timeout) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              ZohoSalesIQ.ChatActions.setTimeout((long)timeout*1000);
          }
      });
  }

  private void completeChatAction(final String uuid) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              SalesIQCustomActionListener listener;
              listener = actionsList.get(uuid);
              if (listener != null){
                  listener.onSuccess();
              }
              if (actionsList != null) {
                  actionsList.remove(uuid);
              }
          }
      });
  }

  private void completeChatActionWithMessage(final String uuid, final boolean success, final String message) {
      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
          public void run() {
              SalesIQCustomActionListener listener = actionsList.get(uuid);
              if (listener != null){
                  if (success) {
                      if (message != null) {
                          listener.onSuccess(message);
                      }
                      else{
                          listener.onSuccess();
                      }
                  }
                  else{
                      if (message != null) {
                          listener.onFailure(message);
                      }
                      else{
                          listener.onFailure();
                      }
                  }
              }
              if (actionsList != null) {
                  actionsList.remove(uuid);
              }
          }
      });
  }

  private void setVisitorLocation(final JSONObject visitorLocation) throws JSONException {
      SIQVisitorLocation siqVisitorLocation = new SIQVisitorLocation();

      if (visitorLocation.has("latitude")){
          siqVisitorLocation.setLatitude(LiveChatUtil.getDouble(visitorLocation.get("latitude")));         // No I18N
      }
      if (visitorLocation.has("longitude")){
          siqVisitorLocation.setLongitude(LiveChatUtil.getDouble(visitorLocation.get("longitude")));         // No I18N
      }
      if (visitorLocation.has("country")){
          siqVisitorLocation.setCountry(LiveChatUtil.getString(visitorLocation.get("country")));         // No I18N
      }
      if (visitorLocation.has("city")){
          siqVisitorLocation.setCity(LiveChatUtil.getString(visitorLocation.get("city")));         // No I18N
      }
      if (visitorLocation.has("state")){
          siqVisitorLocation.setState(LiveChatUtil.getString(visitorLocation.get("state")));         // No I18N
      }
      if (visitorLocation.has("countryCode")){
          siqVisitorLocation.setCountryCode(LiveChatUtil.getString(visitorLocation.get("countryCode")));         // No I18N
      }
      if (visitorLocation.has("zipCode")){
          siqVisitorLocation.setZipCode(LiveChatUtil.getString(visitorLocation.get("zipCode")));         // No I18N
      }
      ZohoSalesIQ.Visitor.setLocation(siqVisitorLocation);
  }

  private void syncThemeWithOS(boolean sync){
    Handler handler = new Handler(Looper.getMainLooper());
    handler.post(new Runnable() {
      public void run() {
        ZohoSalesIQ.syncThemeWithOS(sync);
      }
    });
  }

  private void isMultipleOpenChatRestricted(CallbackContext callbackContext){
    Handler handler = new Handler(Looper.getMainLooper());
    handler.post(new Runnable() {
      public void run() {
        if (ZohoSalesIQ.Chat.isMultipleOpenRestricted()){
          callbackContext.success(1);
        } else {
          callbackContext.success(0);
        }
      }
    });
  }

  public static void handleNotification(final Application application, final Map extras, final CallbackContext callbackContext) {
      SharedPreferences sharedPreferences = application.getSharedPreferences("siq_session", 0);         // No I18N
      final String appKey = sharedPreferences.getString("salesiq_appkey", null);         // No I18N
      final String accessKey = sharedPreferences.getString("salesiq_accesskey", null);         // No I18N
      if (appKey != null && accessKey != null) {
          Handler handler = new Handler(Looper.getMainLooper());
          handler.post(new Runnable() {
              public void run() {
                  initSalesIQ(application, null, appKey, accessKey, callbackContext);
                  ZohoSalesIQ.Notification.handle(application, extras, 0);
              }
          });
      }
  }

  public static void enablePush(String token, Boolean testdevice) {
      fcmtoken = token;
      istestdevice = testdevice;
  }

  private static void initSalesIQ(final Application application, final Activity activity, final String appKey, final String accessKey, final CallbackContext callbackContext) {
      if (application != null) {
          ZohoSalesIQ.init(application, appKey, accessKey, null, new OnInitCompleteListener() {
              @Override
              public void onInitComplete() {
                  if (fcmtoken != null) {
                      ZohoSalesIQ.Notification.enablePush(fcmtoken, istestdevice);
                  }
                  if (activity != null) {
                      Handler handler = new Handler(Looper.getMainLooper());
                      handler.post(new Runnable() {
                          public void run() {
                              ZohoSalesIQ.getApplicationManager().refreshChatBubble();
                          }
                      });
                  }
                  callbackContext.success();
              }

              @Override
              public void onInitError() {
                  callbackContext.error(SalesIQConstants.LocalAPI.NO_INTERNET_MESSAGE);
              }
          });
          ZohoSalesIQ.setPlatformName("Cordova-Android");         // No I18N
          if (activity != null) {
              ZohoSalesIQ.getApplicationManager().setCurrentActivity(activity);
              ZohoSalesIQ.getApplicationManager().setAppActivity(activity);
          }
          ZohoSalesIQ.forceInitialiseSDK();
      }
  }

    private void eventEmitter(String event, Object value){
        String pluginName = "ZohoSalesIQ";         // No I18N
        cordova.getActivity().runOnUiThread(() -> webView.loadUrl("javascript:"+pluginName+".sendEvent('"+event+"','"+value+"');"));         // No I18N
    }

    private Boolean isValidFilterName(String filterName){
        for (ConversationType type : ConversationType.values()) {
            if (type.name().equals(filterName)) {
                return true;
            }
        }
        return false;
    }

    private ConversationType getFilterName(String filter){
        switch (filter){
            case TYPE_CONNECTED : return ConversationType.CONNECTED;
            case TYPE_WAITING : return ConversationType.WAITING;
            case TYPE_OPEN : return ConversationType.OPEN;
            case TYPE_CLOSED : return ConversationType.CLOSED;
            case TYPE_MISSED : return ConversationType.MISSED;
            case TYPE_ENDED : return ConversationType.ENDED;
            default: return ConversationType.CONNECTED;
        }
    }

    public HashMap getChatMapObject(VisitorChat chat){
        HashMap visitorMap = new HashMap();
        visitorMap.put("id", chat.getChatID());         // No I18N
        visitorMap.put("unreadCount", chat.getUnreadCount());         // No I18N
        visitorMap.put("isBotAttender", chat.isBotAttender());         // No I18N
        if (chat.getQueuePosition() > 0) {
          visitorMap.put("queuePosition", chat.getQueuePosition());         // No I18N
        }
        if (chat.getQuestion() != null){
            visitorMap.put("question", chat.getQuestion());         // No I18N
        }
        if (chat.getDepartmentName() != null){
            visitorMap.put("departmentName", chat.getDepartmentName());         // No I18N
        }
        if (chat.getChatStatus() != null){
            visitorMap.put("status", chat.getChatStatus());         // No I18N
        }
        if (chat.getLastMessage() != null){
            visitorMap.put("lastMessage", chat.getLastMessage());         // No I18N
        }
        if (chat.getLastMessageSender() != null){
            visitorMap.put("lastMessageSender", chat.getLastMessageSender());         // No I18N
        }
        if (chat.getLastMessageTime() > 0){
            long lastMessageTime = chat.getLastMessageTime();
            visitorMap.put("lastMessageTime", lastMessageTime);         // No I18N
        }
        if (chat.getAttenderName() != null) {
            visitorMap.put("attenderName", chat.getAttenderName());         // No I18N
        }
        if (chat.getAttenderId() != null) {
            visitorMap.put("attenderID", chat.getAttenderId());         // No I18N
        }
        if (chat.getAttenderEmail() != null) {
            visitorMap.put("attenderEmail", chat.getAttenderEmail());         // No I18N
        }
        if (chat.getFeedbackMessage() != null) {
            visitorMap.put("feedback", chat.getFeedbackMessage());         // No I18N
        }
        if (chat.getRating() != null) {
            visitorMap.put("rating", chat.getRating());         // No I18N
        }

        return visitorMap;
    }

  public HashMap getDepartmentMapObject(SIQDepartment department) {
    HashMap departmentMap = new HashMap();
    departmentMap.put("id", department.id);                                                            // No I18N
    departmentMap.put("name", department.name);                                                        // No I18N
    departmentMap.put("available", department.available);                                              // No I18N
    return departmentMap;
  }

    public HashMap getArticleMapObject(SalesIQArticle article) {
        HashMap articleMap = new HashMap();
        articleMap.put("id", article.getId());         // No I18N
        articleMap.put("name", article.getTitle());         // No I18N
        articleMap.put("likeCount", article.getLiked());         // No I18N
        articleMap.put("dislikeCount", article.getDisliked());         // No I18N
        articleMap.put("viewCount", article.getViewed());         // No I18N
        if (article.getCategory_id() != null) {
            articleMap.put("categoryID", article.getCategory_id());         // No I18N
        }
        if (article.getCategoryName() != null) {
            articleMap.put("categoryName", article.getCategoryName());         // No I18N
        }
        return articleMap;
    }

    public HashMap getVisitorInfoObject(SIQVisitor siqVisitor){
        HashMap visitorInfoMap = new HashMap();
        if (siqVisitor.getName() != null) {
            visitorInfoMap.put("name", siqVisitor.getName());         // No I18N
        }
        if (siqVisitor.getEmail() != null) {
            visitorInfoMap.put("email", siqVisitor.getEmail());         // No I18N
        }
        if (siqVisitor.getPhone() != null) {
            visitorInfoMap.put("phone", siqVisitor.getPhone());         // No I18N
        }
        visitorInfoMap.put("numberOfChats", LiveChatUtil.getString(siqVisitor.getNumberOfChats()));         // No I18N
        if (siqVisitor.getCity() != null) {
            visitorInfoMap.put("city", siqVisitor.getCity());         // No I18N
        }
        if (siqVisitor.getIp() != null) {
            visitorInfoMap.put("ip", siqVisitor.getIp());         // No I18N
        }
        if (siqVisitor.getFirstVisitTime() != null) {
            Date firstVisitTime = siqVisitor.getFirstVisitTime();
            visitorInfoMap.put("firstVisitTime", LiveChatUtil.getString(firstVisitTime.getTime()));         // No I18N
        }
        if (siqVisitor.getLastVisitTime() != null) {
            Date lastVisitTime = siqVisitor.getLastVisitTime();
            visitorInfoMap.put("lastVisitTime", LiveChatUtil.getString(lastVisitTime.getTime()));         // No I18N
        }
        if (siqVisitor.getRegion() != null) {
            visitorInfoMap.put("region", siqVisitor.getRegion());         // No I18N
        }
        if (siqVisitor.getOs() != null) {
            visitorInfoMap.put("os", siqVisitor.getOs());         // No I18N
        }
        if (siqVisitor.getCountryCode() != null) {
            visitorInfoMap.put("countryCode", siqVisitor.getCountryCode());         // No I18N
        }
        if (siqVisitor.getBrowser() != null) {
            visitorInfoMap.put("browser", siqVisitor.getBrowser());         // No I18N
        }
        if (siqVisitor.getTotalTimeSpent() != null) {
            visitorInfoMap.put("totalTimeSpent", siqVisitor.getTotalTimeSpent());         // No I18N
        }
        visitorInfoMap.put("numberOfVisits", LiveChatUtil.getString(siqVisitor.getNumberOfVisits()));         // No I18N
        visitorInfoMap.put("noOfDaysVisited",LiveChatUtil.getString(siqVisitor.getNoOfDaysVisited()));         // No I18N
        if (siqVisitor.getState() != null) {
            visitorInfoMap.put("state", siqVisitor.getState());         // No I18N
        }
        if (siqVisitor.getSearchEngine() != null) {
            visitorInfoMap.put("searchEngine", siqVisitor.getSearchEngine());         // No I18N
        }
        if (siqVisitor.getSearchQuery() != null) {
            visitorInfoMap.put("searchQuery", siqVisitor.getSearchQuery());         // No I18N
        }
        return visitorInfoMap;
    }

    public class ZohoSalesIQPluginListener implements SalesIQListener, SalesIQChatListener, SalesIQFAQListener, SalesIQActionListener {

        @Override
        public void handleFeedback(VisitorChat visitorChat) {
            HashMap visitorMap = getChatMapObject(visitorChat);
            Gson gson = new Gson();
            String json = gson.toJson(visitorMap);
            eventEmitter(FEEDBACK_RECEIVED, json);
        }

      @Override
      public void handleQueuePositionChange(VisitorChat visitorChat) {
        HashMap visitorMap = getChatMapObject(visitorChat);
        Gson gson = new Gson();
        String json = gson.toJson(visitorMap);
        eventEmitter(CHAT_QUEUE_POSITION_CHANGED, json);
      }

      @Override
        public void handleRating(VisitorChat visitorChat) {
            HashMap visitorMap = getChatMapObject(visitorChat);
            Gson gson = new Gson();
            String json = gson.toJson(visitorMap);
            eventEmitter(RATING_RECEIVED, json);
        }

        @Override
        public void handleOperatorsOnline() {
            eventEmitter(OPERATORS_ONLINE, null);
        }

        @Override
        public void handleOperatorsOffline() {
            eventEmitter(OPERATORS_OFFLINE, null);
        }

        @Override
        public void handleArticleOpened(String id) {
            eventEmitter(ARTICLE_OPENED, id);
        }

        @Override
        public void handleArticleClosed(String id) {
            eventEmitter(ARTICLE_CLOSED, id);
        }

        @Override
        public void handleArticleLiked(String id) {
            eventEmitter(ARTICLE_LIKED, id);
        }

        @Override
        public void handleArticleDisliked(String id) {
            eventEmitter(ARTICLE_DISLIKED, id);
        }

        @Override
        public void handleSupportOpen() {
            eventEmitter(SUPPORT_OPENED, null);
        }

        @Override
        public void handleSupportClose() {
            eventEmitter(SUPPORT_CLOSED, null);
        }

        @Override
        public void handleChatViewOpen(String id) {
            eventEmitter(CHATVIEW_OPENED, id);
        }

        @Override
        public void handleChatViewClose(String id) {
            eventEmitter(CHATVIEW_CLOSED, id);
        }

        @Override
        public void handleIPBlock() {
            eventEmitter(VISITOR_IPBLOCKED, null);
        }

        @Override
        public void handleTrigger(String triggerName, SIQVisitor visitor) {
            HashMap visitorMap = new HashMap();
            visitorMap.put("triggerName",triggerName);
            visitorMap.put("visitorInformation",getVisitorInfoObject(visitor));
            Gson gson = new Gson();
            String json = gson.toJson(visitorMap);
            eventEmitter(CUSTOMTRIGGER, json);
        }

        @Override
        public void handleChatOpened(VisitorChat visitorChat) {
            HashMap visitorMap = getChatMapObject(visitorChat);
            Gson gson = new Gson();
            String json = gson.toJson(visitorMap);
            eventEmitter(CHAT_OPENED, json);
        }

        @Override
        public void handleChatClosed(VisitorChat visitorChat) {
            HashMap visitorMap = getChatMapObject(visitorChat);
            Gson gson = new Gson();
            String json = gson.toJson(visitorMap);
            eventEmitter(CHAT_CLOSED, json);
        }

        @Override
        public void handleChatAttended(VisitorChat visitorChat) {
            HashMap visitorMap = getChatMapObject(visitorChat);
            Gson gson = new Gson();
            String json = gson.toJson(visitorMap);
            eventEmitter(CHAT_ATTENDED, json);
        }

        @Override
        public void handleChatMissed(VisitorChat visitorChat) {
            HashMap visitorMap = getChatMapObject(visitorChat);
            Gson gson = new Gson();
            String json = gson.toJson(visitorMap);
            eventEmitter(CHAT_MISSED, json);
        }

        @Override
        public void handleChatReOpened(VisitorChat visitorChat) {
            HashMap visitorMap = getChatMapObject(visitorChat);
            Gson gson = new Gson();
            String json = gson.toJson(visitorMap);
            eventEmitter(CHAT_REOPENED, json);
        }

        @Override
        public void handleCustomAction(SalesIQCustomAction salesIQCustomAction, final SalesIQCustomActionListener salesIQCustomActionListener) {
            UUID uuid = UUID.randomUUID();

            final HashMap actionDetailsMap = new HashMap();
            actionDetailsMap.put("uuid", uuid.toString());         // No I18N
            actionDetailsMap.put("elementID", salesIQCustomAction.elementID);         // No I18N
            actionDetailsMap.put("label", salesIQCustomAction.label);         // No I18N
            actionDetailsMap.put("name", salesIQCustomAction.name);         // No I18N
            actionDetailsMap.put("clientActionName", salesIQCustomAction.clientActionName);         // No I18N

            actionsList.put(uuid.toString(), salesIQCustomActionListener);

            Gson gson = new Gson();
            String json = gson.toJson(actionDetailsMap);
            eventEmitter(PERFORM_CHATACTION, json);
        }
    }
}
