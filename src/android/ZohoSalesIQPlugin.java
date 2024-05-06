package com.zohosalesiq.mobilisten;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.zoho.commons.ChatComponent;
import com.zoho.commons.LauncherModes;
import com.zoho.commons.LauncherProperties;
import com.zoho.commons.OnInitCompleteListener;
import com.zoho.livechat.android.SIQDepartment;
import com.zoho.livechat.android.SIQVisitor;
import com.zoho.livechat.android.SIQVisitorLocation;
import com.zoho.livechat.android.SalesIQCustomAction;
import com.zoho.livechat.android.VisitorChat;
import com.zoho.livechat.android.constants.ConversationType;
import com.zoho.livechat.android.constants.SalesIQConstants;
import com.zoho.livechat.android.exception.InvalidEmailException;
import com.zoho.livechat.android.exception.InvalidVisitorIDException;
import com.zoho.livechat.android.listeners.ConversationListener;
import com.zoho.livechat.android.listeners.DepartmentListener;
import com.zoho.livechat.android.listeners.FAQCategoryListener;
import com.zoho.livechat.android.listeners.FAQListener;
import com.zoho.livechat.android.listeners.OperatorImageListener;
import com.zoho.livechat.android.listeners.SalesIQActionListener;
import com.zoho.livechat.android.listeners.SalesIQChatListener;
import com.zoho.livechat.android.listeners.SalesIQCustomActionListener;
import com.zoho.livechat.android.listeners.SalesIQListener;
import com.zoho.livechat.android.models.SalesIQArticle;
import com.zoho.livechat.android.models.SalesIQArticleCategory;
import com.zoho.livechat.android.modules.common.DataModule;
import com.zoho.livechat.android.modules.common.ui.LauncherUtil;
import com.zoho.livechat.android.modules.knowledgebase.ui.entities.Resource;
import com.zoho.livechat.android.modules.knowledgebase.ui.entities.ResourceCategory;
import com.zoho.livechat.android.modules.knowledgebase.ui.entities.ResourceDepartment;
import com.zoho.livechat.android.modules.knowledgebase.ui.listeners.OpenResourceListener;
import com.zoho.livechat.android.modules.knowledgebase.ui.listeners.ResourceCategoryListener;
import com.zoho.livechat.android.modules.knowledgebase.ui.listeners.ResourceDepartmentsListener;
import com.zoho.livechat.android.modules.knowledgebase.ui.listeners.ResourceListener;
import com.zoho.livechat.android.modules.knowledgebase.ui.listeners.ResourcesListener;
import com.zoho.livechat.android.modules.knowledgebase.ui.listeners.SalesIQKnowledgeBaseListener;
import com.zoho.livechat.android.operation.SalesIQApplicationManager;
import com.zoho.livechat.android.utils.LiveChatUtil;
import com.zoho.salesiqembed.ZohoSalesIQ;
import com.zoho.salesiqembed.ktx.GsonExtensionsKt;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ZohoSalesIQPlugin extends CordovaPlugin {

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
    private static final String BOT_TRIGGER = "BOT_TRIGGER";         // No I18N
    private static final String HANDLE_CUSTOM_LAUNCHER_VISIBILITY = "HANDLE_CUSTOM_LAUNCHER_VISIBILITY";         // No I18N
    private static final String CHAT_QUEUE_POSITION_CHANGED =
            "CHAT_QUEUE_POSITION_CHANGED";         // No I18N
    private static final String HANDLE_URL = "HANDLE_URL";         // No I18N

    private static final String TYPE_OPEN = "OPEN";         // No I18N
    private static final String TYPE_CONNECTED = "CONNECTED";         // No I18N
    private static final String TYPE_CLOSED = "CLOSED";         // No I18N
    private static final String TYPE_ENDED = "ENDED";         // No I18N
    private static final String TYPE_MISSED = "MISSED";         // No I18N
    private static final String TYPE_WAITING = "WAITING";         // No I18N

    private static final int INVALID_FILTER_CODE = 604;
    private static final String INVALID_FILTER_TYPE = "invalid filter type";         // No I18N

    private static final String LAUNCHER_HORIZONTAL_LEFT = "LAUNCHER_HORIZONTAL_LEFT";  // No I18N
    private static final String LAUNCHER_HORIZONTAL_RIGHT = "LAUNCHER_HORIZONTAL_RIGHT";    // No I18N
    private static final String LAUNCHER_VERTICAL_TOP = "LAUNCHER_VERTICAL_TOP";    // No I18N
    private static final String LAUNCHER_VERTICAL_BOTTOM = "LAUNCHER_VERTICAL_BOTTOM";  // No I18N
    private static final String LAUNCHER_VISIBILITY_MODE_ALWAYS = "LAUNCHER_VISIBILITY_MODE_ALWAYS";    // No I18N
    private static final String LAUNCHER_VISIBILITY_MODE_NEVER = "LAUNCHER_VISIBILITY_MODE_NEVER";  // No I18N
    private static final String LAUNCHER_VISIBILITY_MODE_WHEN_ACTIVE_CHAT = "LAUNCHER_VISIBILITY_MODE_WHEN_ACTIVE_CHAT";    // No I18N

    private static final String EVENT_OPEN_URL = "EVENT_OPEN_URL";  // No I18N
    private static final String EVENT_COMPLETE_CHAT_ACTION = "EVENT_COMPLETE_CHAT_ACTION";// No I18N

    private static final String EVENT_RESOURCE_LIKED = "RESOURCE_LIKED";         // No I18N
    private static final String EVENT_RESOURCE_DISLIKED = "RESOURCE_DISLIKED";         // No I18N
    private static final String EVENT_RESOURCE_OPENED = "RESOURCE_OPENED";         // No I18N
    private static final String EVENT_RESOURCE_CLOSED = "RESOURCE_CLOSED";         // No I18N

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    private static Hashtable<String, SalesIQCustomActionListener> actionsList = new Hashtable<>();

    private static boolean shouldOpenUrl = true;

    enum Tab {
        CONVERSATIONS("TAB_CONVERSATIONS"),
        @Deprecated FAQ("TAB_FAQ"),
        KNOWLEDGE_BASE("TAB_KNOWLEDGE_BASE");

        final String name;

        Tab(String name) {
            this.name = name;
        }
    }

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        if (action != null) {
            if (action.equalsIgnoreCase("init")) {
                this.init(data.get(0).toString(), data.get(1).toString(), callbackContext);
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
                this.setVisitorNameVisibility((boolean) data.get(0));
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
                this.showOperatorImageInChat((boolean) data.get(0));
            }
            if (action.equals("setFeedbackVisibility")) {
                this.setFeedbackVisibility((boolean) data.get(0));
            }
            if (action.equals("setRatingVisibility")) {
                this.setRatingVisibility((boolean) data.get(0));
            }
            if (action.equals("showOperatorImageInLauncher")) {
                this.showOperatorImageInLauncher((boolean) data.get(0));
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
                this.showOfflineMessage((boolean) data.get(0));
            }
            if (action.equals("endChat")) {
                this.endChat(data.get(0).toString());
            }
            if (action.equals("showLauncher")) {
                this.showLauncher((boolean) data.get(0));
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
                this.setVisitorAddInfo(data.get(0).toString(), data.get(1).toString());
            }
            if (action.equals("setQuestion")) {
                this.setQuestion(data.get(0).toString());
            }
            if (action.equals("startChat")) {
                this.startChat(data.get(0).toString());
            }
            if (action.equals("setConversationVisibility")) {
                this.setConversationVisibility((boolean) data.get(0));
            }
            if (action.equals("setConversationListTitle")) {
                this.setConversationListTitle(data.get(0).toString());
            }
            if (action.equals("setFAQVisibility")) {
                this.setFAQVisibility((boolean) data.get(0));
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
                this.performCustomAction(data.get(0).toString(), LiveChatUtil.getBoolean(data.get(1)));
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
                            fetchAttenderImage(LiveChatUtil.getString(data.get(0)),
                                    LiveChatUtil.getBoolean(data.get(1)), callbackContext);
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
                this.getChatsWithFilter(LiveChatUtil.getString(data.get(0)), callbackContext);
            }
            if (action.equals("getDepartments")) {
                this.getDepartments(callbackContext);
            }
            if (action.equals("getArticles")) {
                this.getArticles(callbackContext);
            }
            if (action.equals("getArticlesWithCategoryID")) {
                this.getArticlesWithCategoryID(LiveChatUtil.getString(data.get(0)),
                        callbackContext);
            }
            if (action.equals("getCategories")) {
                this.getCategories(callbackContext);
            }
            if (action.equals("openArticle")) {
                this.openArticle(LiveChatUtil.getString(data.get(0)), callbackContext);
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
                this.completeChatActionWithMessage(data.get(0).toString(), (boolean) data.get(1),
                        data.get(2).toString());
            }
            if (action.equals("setVisitorLocation")) {
                this.setVisitorLocation((JSONObject) data.get(0));
            }
            if (action.equals("syncThemeWithOSForAndroid")) {
                this.syncThemeWithOS((boolean) data.get(0));
            }
            if (action.equals("isMultipleOpenChatRestricted")) {
                this.isMultipleOpenChatRestricted(callbackContext);
            }
            if (action.equals("printDebugLogsForAndroid")) {
                this.printDebugLogsForAndroid((boolean) data.get(0));
            }
            if (action.equals("sendEvent")) {
                this.sendEvent((String) data.get(0), (JSONArray) data.get(1));
            }
            if (action.equals("shouldOpenUrl")) {
                this.shouldOpenUrl((boolean) data.get(0));
            }
            if (action.equals("setTabOrder")) {
                this.setTabOrder((JSONArray) data.get(0));
            }
            if (action.equals("setNotificationIconForAndroid")) {
                this.setNotificationIconForAndroid((String) data.get(0));
            }
            if (action.equals("setLoggerEnabled")) {
                this.setLoggerEnabled((boolean) data.get(0));
            }
            if (action.equals("isLoggerEnabled")) {
                this.isLoggerEnabled(callbackContext);
            }
            if (action.equals("setLauncherPropertiesForAndroid")) {
                this.setLauncherPropertiesForAndroid((JSONObject) data.get(0));
            }
            if (action.equals("refreshLauncher")) {
                this.refreshLauncher();
            }
            if (action.equals("setLauncherIconForAndroid")) {
                this.setLauncherIconForAndroid((String) data.get(0));
            }
            if (action.equals("setThemeForAndroid")) {
                this.setThemeForAndroid((String) data.get(0));
            }
            if (action.equals("dismissUI")) {
                this.dismissUI();
            }

            if (action.contains("Chat")) {
                Chat.handleMethodCalls(action, data, callbackContext);
            }
            if (action.contains("Launcher")) {
                Launcher.handleMethodCalls(action, data, callbackContext);
            }
            if (action.contains("KnowledgeBase")) {
                KnowledgeBase.handleKnowledgeBaseCalls(action, data, callbackContext);
            }
            return true;
        }
        return false;
    }

    static class Chat {
        public static void handleMethodCalls(String action, JSONArray data, CallbackContext callbackContext) {
            try {
                if (action.equals("showChatFeedbackAfterSkip")) {  // No I18N
                    ZohoSalesIQ.Chat.showFeedbackAfterSkip(LiveChatUtil.getBoolean(data.get(0)));
                } else if (action.equals("showChatFeedbackUpTo")) {    // No I18N
                    ZohoSalesIQ.Chat.showFeedback(LiveChatUtil.getInteger(data.get(0)));
                } else if (action.contains("isChatEnabled")) {
                    Chat.isChatEnabled(callbackContext);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        private static void isChatEnabled(CallbackContext callbackContext) {
            Boolean status = ZohoSalesIQ.isSDKEnabled();
            if (callbackContext != null) {
                if (status) {
                    callbackContext.success(1);
                } else {
                    callbackContext.success(0);
                }
            }
        }
    }

    static class Launcher {

        private static ZohoSalesIQ.Launcher.VisibilityMode getVisibilityMode(final String mode) {
            ZohoSalesIQ.Launcher.VisibilityMode visibilityMode = ZohoSalesIQ.Launcher.VisibilityMode.NEVER;
            switch (mode) {
                case LAUNCHER_VISIBILITY_MODE_ALWAYS:
                    visibilityMode = ZohoSalesIQ.Launcher.VisibilityMode.ALWAYS;
                    break;
                case LAUNCHER_VISIBILITY_MODE_WHEN_ACTIVE_CHAT:
                    visibilityMode = ZohoSalesIQ.Launcher.VisibilityMode.WHEN_ACTIVE_CHAT;
                    break;
            }
            return visibilityMode;
        }

        public static void handleMethodCalls(final String action, final JSONArray data, final CallbackContext callbackContext) {
            try {
                switch (action) {
                    case "setLauncherVisibilityMode":
                        ZohoSalesIQ.Launcher.show(getVisibilityMode(LiveChatUtil.getString(data.get(0))));
                        break;
                    case "setVisibilityModeToCustomLauncher":
                        ZohoSalesIQ.Launcher.setVisibilityModeToCustomLauncher(getVisibilityMode(LiveChatUtil.getString(data.get(0))));
                        break;
                    case "enableLauncherDragToDismiss":
                        ZohoSalesIQ.Launcher.enableDragToDismiss(LiveChatUtil.getBoolean(data.get(0)));
                        break;
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void init(final String appKey, final String accessKey,
                      final CallbackContext callbackContext) {
        final Application context = this.cordova.getActivity().getApplication();
        final Activity activity = this.cordova.getActivity();
        HANDLER.post(new Runnable() {
            public void run() {
                initSalesIQ(context, activity, appKey, accessKey, callbackContext);
                ZohoSalesIQ.setListener(new ZohoSalesIQPluginListener());
                ZohoSalesIQ.Chat.setListener(new ZohoSalesIQPluginListener());
                ZohoSalesIQ.KnowledgeBase.setListener(new ZohoSalesIQPluginListener());
                ZohoSalesIQ.ChatActions.setListener(new ZohoSalesIQPluginListener());
            }
        });
    }

    private void enableScreenshotOption() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                ZohoSalesIQ.Chat.setVisibility(ChatComponent.screenshot, true);
            }
        });
    }

    private void disableScreenshotOption() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                ZohoSalesIQ.Chat.setVisibility(ChatComponent.screenshot, false);
            }
        });
    }

    private void enablePreChatForms() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                ZohoSalesIQ.Chat.setVisibility(ChatComponent.prechatForm, true);
            }
        });
    }

    private void disablePreChatForms() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                ZohoSalesIQ.Chat.setVisibility(ChatComponent.prechatForm, false);
            }
        });
    }

    private void setVisitorNameVisibility(final boolean visibility) {
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
                ArrayList<String> departmentList = new ArrayList<>();
                if (department != null) {
                    try {
                        for (int i = 0; i < department.length(); i++) {
                            departmentList.add(department.getString(i));
                        }
                        ZohoSalesIQ.Chat.setDepartments(departmentList);
                    } catch (JSONException e) {
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
                if (activity != null && ZohoSalesIQ.getApplicationManager() != null) {
                    if (ZohoSalesIQ.getApplicationManager().getCurrentActivity() == null) {
                        ZohoSalesIQ.getApplicationManager().setCurrentActivity(activity);
                    }
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

    private void showLauncher(final Boolean visible) {
        HANDLER.post(new Runnable() {
            public void run() {
                ZohoSalesIQ.showLauncher(visible);
                Activity activity = cordova.getActivity();
                if (activity != null && ZohoSalesIQ.getApplicationManager() != null) {
                    if (ZohoSalesIQ.getApplicationManager().getCurrentActivity() == null) {
                        ZohoSalesIQ.getApplicationManager().setCurrentActivity(activity);
                    }
                    LauncherUtil.refreshLauncher();
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
                ZohoSalesIQ.KnowledgeBase.setVisibility(ZohoSalesIQ.ResourceType.Articles, visible);
            }
        });
    }

    private void registerVisitor(final String uniqueid) {
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

    private void unregisterVisitor() {
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

    private void performCustomAction(final String actionName, final boolean shouldOpenChatWindow) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                ZohoSalesIQ.Tracking.setCustomAction(actionName, shouldOpenChatWindow);
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

    private void fetchAttenderImage(String attenderId, Boolean defaultImage,
                                    final CallbackContext imageCallback) {
        ZohoSalesIQ.Chat.fetchAttenderImage(attenderId, defaultImage, new OperatorImageListener() {
            @Override
            public void onSuccess(Drawable drawable) {
                if (drawable != null) {
                    Bitmap bitmap;

                    try {
                        bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

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
                HashMap<String, Object> errorMap = new HashMap<>();
                errorMap.put("code", code);         // No I18N
                errorMap.put("message", message);         // No I18N
                JSONObject errorObject = new JSONObject(errorMap);
                imageCallback.error(errorObject);
            }
        });
    }

    private void getChats(final CallbackContext listCallback) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                ZohoSalesIQ.Chat.getList(new ConversationListener() {
                    @Override
                    public void onSuccess(ArrayList<VisitorChat> arrayList) {
                        if (arrayList != null) {
                            ArrayList<HashMap> array = new ArrayList<>();
                            for (int i = 0; i < arrayList.size(); i++) {
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
                        HashMap<String, Object> errorMap = new HashMap<>();
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
                                if (arrayList != null) {
                                    ArrayList<HashMap> array = new ArrayList<>();
                                    for (int i = 0; i < arrayList.size(); i++) {
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
                                HashMap<String, Object> errorMap = new HashMap<>();
                                errorMap.put("code", code);         // No I18N
                                errorMap.put("message", message);         // No I18N
                                JSONObject errorObject = new JSONObject(errorMap);
                                listCallback.error(errorObject);
                            }
                        });
                    } else {
                        HashMap<String, Object> errorMap = new HashMap<>();
                        errorMap.put("code", INVALID_FILTER_CODE);         // No I18N
                        errorMap.put("message", INVALID_FILTER_TYPE);         // No I18N
                        JSONObject errorObject = new JSONObject(errorMap);
                        listCallback.error(errorObject);
                    }
                } catch (Exception e) {
                    LiveChatUtil.log(e);
                }
            }
        });
    }

    private void getDepartments(final CallbackContext departmentCallback) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                ZohoSalesIQ.Chat.getDepartments(new DepartmentListener() {
                    @Override
                    public void onSuccess(ArrayList<SIQDepartment> departmentsList) {
                        if (departmentsList != null) {
                            ArrayList<HashMap> array = new ArrayList<>();
                            for (int i = 0; i < departmentsList.size(); i++) {
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
                        HashMap<String, Object> errorMap = new HashMap<>();
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
                            for (int i = 0; i < articlesList.size(); i++) {
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
                        HashMap<String, Object> errorMap = new HashMap<>();
                        errorMap.put("code", code);         // No I18N
                        errorMap.put("message", message);         // No I18N
                        JSONObject errorObject = new JSONObject(errorMap);
                        articlesCallback.error(errorObject);
                    }
                });
            }
        });
    }

    private void getArticlesWithCategoryID(final String categoryId,
                                           final CallbackContext articlesCallback) {
        ZohoSalesIQ.KnowledgeBase.getResources(ZohoSalesIQ.ResourceType.Articles, null, categoryId, null, false, new ResourcesListener() {
                    @Override
                    public void onSuccess(@NonNull List<Resource> resources, boolean b) {
                        ArrayList<HashMap> array = new ArrayList<>();
                        for (int i = 0; i < resources.size(); i++) {
                            array.add(getArticleMapObject(resources.get(i)));
                        }
                        articlesCallback.success(new JSONArray(array));
                    }

                    @Override
                    public void onFailure(int code, @Nullable String message) {
                        HashMap<String, Object> errorMap = new HashMap<>();
                        errorMap.put("code", code);         // No I18N
                        errorMap.put("message", message);         // No I18N
                        articlesCallback.error(new JSONObject(errorMap));
                    }
                }
        );
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

                            for (int i = 0; i < categoryList.size(); i++) {
                                SalesIQArticleCategory category = categoryList.get(i);

                                HashMap<String, Object> categoryMap = new HashMap<>();
                                categoryMap.put("id", category.getCategoryId());         // No I18N
                                categoryMap.put("name", category.getCategoryName());         //
                                // No I18N
                                categoryMap.put("articleCount", category.getCount());         //
                                // No I18N
                                array.add(categoryMap);
                            }
                            JSONArray jsonArray = new JSONArray(array);
                            categoryCallback.success(jsonArray);
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        HashMap<String, Object> errorMap = new HashMap<>();
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
                ZohoSalesIQ.KnowledgeBase.open(ZohoSalesIQ.ResourceType.Articles, id, new OpenResourceListener() {
                    @Override
                    public void onSuccess() {
                        articlesCallback.success();
                    }

                    @Override
                    public void onFailure(int code, @Nullable String message) {
                        HashMap<String, Object> errorMap = new HashMap<>();
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
                ZohoSalesIQ.ChatActions.setTimeout((long) timeout * 1000);
            }
        });
    }

    private void completeChatAction(final String uuid) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                SalesIQCustomActionListener listener;
                listener = actionsList.get(uuid);
                if (listener != null) {
                    listener.onSuccess();
                }
                if (actionsList != null) {
                    actionsList.remove(uuid);
                }
            }
        });
    }

    private void completeChatActionWithMessage(final String uuid, final boolean success,
                                               final String message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                SalesIQCustomActionListener listener = actionsList.get(uuid);
                if (listener != null) {
                    if (success) {
                        if (message != null) {
                            listener.onSuccess(message);
                        } else {
                            listener.onSuccess();
                        }
                    } else {
                        if (message != null) {
                            listener.onFailure(message);
                        } else {
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

        if (visitorLocation.has("latitude")) {
            siqVisitorLocation.setLatitude(LiveChatUtil.getDouble(visitorLocation.get("latitude")));         // No I18N
        }
        if (visitorLocation.has("longitude")) {
            siqVisitorLocation.setLongitude(LiveChatUtil.getDouble(visitorLocation.get("longitude"
            )));         // No I18N
        }
        if (visitorLocation.has("country")) {
            siqVisitorLocation.setCountry(LiveChatUtil.getString(visitorLocation.get("country")));         // No I18N
        }
        if (visitorLocation.has("city")) {
            siqVisitorLocation.setCity(LiveChatUtil.getString(visitorLocation.get("city")));         // No I18N
        }
        if (visitorLocation.has("state")) {
            siqVisitorLocation.setState(LiveChatUtil.getString(visitorLocation.get("state")));         // No I18N
        }
        if (visitorLocation.has("countryCode")) {
            siqVisitorLocation.setCountryCode(LiveChatUtil.getString(visitorLocation.get(
                    "countryCode")));         // No I18N
        }
        if (visitorLocation.has("zipCode")) {
            siqVisitorLocation.setZipCode(LiveChatUtil.getString(visitorLocation.get("zipCode")));         // No I18N
        }
        ZohoSalesIQ.Visitor.setLocation(siqVisitorLocation);
    }

    private void syncThemeWithOS(boolean sync) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                ZohoSalesIQ.syncThemeWithOS(sync);
            }
        });
    }

    private void isMultipleOpenChatRestricted(CallbackContext callbackContext) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                if (ZohoSalesIQ.Chat.isMultipleOpenRestricted()) {
                    callbackContext.success(1);
                } else {
                    callbackContext.success(0);
                }
            }
        });
    }

    public static void handleNotification(final Application application, final Map extras) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            ZohoSalesIQ.Notification.handle(application, extras);
        });
    }

    public static void enablePush(String token, Boolean testdevice) {
        fcmtoken = token;
        istestdevice = testdevice;
    }

    private static void initSalesIQ(final Application application, final Activity activity,
                                    final String appKey, final String accessKey,
                                    final CallbackContext callbackContext) {
        ZohoSalesIQ.setPlatformName("Cordova-Android");         // No I18N
        if (application != null) {
            ZohoSalesIQ.init(application, appKey, accessKey, null, new OnInitCompleteListener() {
                @Override
                public void onInitComplete() {
                    if (fcmtoken != null) {
                        ZohoSalesIQ.Notification.enablePush(fcmtoken, istestdevice);
                    }
                    if (activity != null && ZohoSalesIQ.getApplicationManager() != null) {
                        LauncherUtil.refreshLauncher();
                    }
                    if (callbackContext != null) {
                        callbackContext.success();
                    }
                }

                @Override
                public void onInitError() {
                    if (callbackContext != null) {
                        callbackContext.error(SalesIQConstants.LocalAPI.NO_INTERNET_MESSAGE);
                    }
                }
            });
            if (activity != null && ZohoSalesIQ.getApplicationManager() != null) {
                if (ZohoSalesIQ.getApplicationManager().getCurrentActivity() == null) {
                    ZohoSalesIQ.getApplicationManager().setCurrentActivity(activity);
                }
                ZohoSalesIQ.getApplicationManager().setAppActivity(activity);
            }
        }
    }

    void eventEmitter(String event, Object value) {
        try {
            Object result = null;
            if (value != null) {
                if (value instanceof Boolean) {
                    result = (boolean) value;
                } else {
                    result = JSONObject.quote(value.toString());
                }
            }
            String pluginName = "ZohoSalesIQ";         // No I18N
            Object finalResult = result;
            cordova.getActivity().runOnUiThread(() -> webView.loadUrl("javascript:" + pluginName +
                    ".sendEventToJs('" + event + "', " + finalResult + ");"));         // No I18N
        } catch (Exception ignore) {

        }
    }

    private Boolean isValidFilterName(String filterName) {
        for (ConversationType type : ConversationType.values()) {
            if (type.name().equals(filterName)) {
                return true;
            }
        }
        return false;
    }

    private ConversationType getFilterName(String filter) {
        switch (filter) {
            case TYPE_CONNECTED:
                return ConversationType.CONNECTED;
            case TYPE_WAITING:
                return ConversationType.WAITING;
            case TYPE_OPEN:
                return ConversationType.OPEN;
            case TYPE_CLOSED:
                return ConversationType.CLOSED;
            case TYPE_MISSED:
                return ConversationType.MISSED;
            case TYPE_ENDED:
                return ConversationType.ENDED;
            default:
                return ConversationType.CONNECTED;
        }
    }

    public HashMap<String, Object> getChatMapObject(VisitorChat chat) {
        HashMap<String, Object> visitorMap = new HashMap<>();
        visitorMap.put("id", chat.getChatID());         // No I18N
        visitorMap.put("unreadCount", chat.getUnreadCount());         // No I18N
        visitorMap.put("isBotAttender", chat.isBotAttender());         // No I18N
        if (chat.getQueuePosition() > 0) {
            visitorMap.put("queuePosition", chat.getQueuePosition());         // No I18N
        }
        if (chat.getQuestion() != null) {
            visitorMap.put("question", chat.getQuestion());         // No I18N
        }
        if (chat.getDepartmentName() != null) {
            visitorMap.put("departmentName", chat.getDepartmentName());         // No I18N
        }
        if (chat.getChatStatus() != null) {
            visitorMap.put("status", chat.getChatStatus());         // No I18N
        }
        if (chat.getLastMessage() != null) {
            visitorMap.put("lastMessage", chat.getLastMessage());         // No I18N
        }
        Map<String, Object> lastMessageMap = new HashMap<>();
        VisitorChat.SalesIQMessage lastMessage = chat.getLastMessage();
        if (lastMessage != null) {
            if (lastMessage.getText() != null) {
                visitorMap.put("lastMessage", lastMessage.getText());         // No I18N
                lastMessageMap.put("text", lastMessage.getText());
            }
            if (lastMessage.getSender() != null) {
                visitorMap.put("lastMessageSender", lastMessage.getSender());         // No I18N
                lastMessageMap.put("sender", lastMessage.getSender());
            }
            if (lastMessage.getTime() != null && lastMessage.getTime() > 0) {
                visitorMap.put("lastMessageTime", LiveChatUtil.getString(lastMessage.getTime()));         // No I18N
                lastMessageMap.put("time", LiveChatUtil.getString(lastMessage.getTime()));         // No I18N
            }
            // lastMessageMap.put("sender_id", lastMessage.getSenderId());
            // lastMessageMap.put("type", lastMessage.getType());
            lastMessageMap.put("is_read", lastMessage.isRead());
            // lastMessageMap.put("sent_by_visitor", lastMessage.getSentByVisitor());
            // if (lastMessage.getStatus() != null) {
            //     String status = null;
            //     switch (lastMessage.getStatus()) {
            //         case Sending:
            //             status = SENDING;
            //             break;
            //         case Uploading:
            //             status = UPLOADING;
            //             break;
            //         case Sent:
            //             status = SENT;
            //             break;
            //         case Failure:
            //             status = FAILURE;
            //             break;
            //     }
            //     lastMessageMap.put("status", status);
            // }
            VisitorChat.SalesIQMessage.SalesIQFile salesIQFile = lastMessage.getFile();
            Map<String, Object> fileMap = new HashMap<>();
            if (salesIQFile != null) {
                fileMap.put("name", salesIQFile.getName());
                fileMap.put("content_type", salesIQFile.getContentType());
                fileMap.put("comment", salesIQFile.getComment());
                fileMap.put("size", salesIQFile.getSize());
                lastMessageMap.put("file", fileMap);
            }
            visitorMap.put("recentMessage", lastMessageMap);         // No I18N
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

    public HashMap<String, Object> getDepartmentMapObject(SIQDepartment department) {
        HashMap<String, Object> departmentMap = new HashMap<>();
        departmentMap.put("id", department.id);                                                            // No I18N
        departmentMap.put("name", department.name);                                                        // No I18N
        departmentMap.put("available", department.available);                                              // No I18N
        return departmentMap;
    }

    public HashMap<String, Object> getArticleMapObject(SalesIQArticle article) {
        HashMap<String, Object> articleMap = new HashMap<>();
        articleMap.put("id", article.getId());         // No I18N
        articleMap.put("name", article.getTitle());         // No I18N
        articleMap.put("likeCount", article.getLiked());         // No I18N
        articleMap.put("dislikeCount", article.getDisliked());         // No I18N
        articleMap.put("viewCount", article.getViewed());         // No I18N
        if (article.getCategoryId() != null) {
            articleMap.put("categoryID", article.getCategoryId());         // No I18N
        }
        if (article.getCategoryName() != null) {
            articleMap.put("categoryName", article.getCategoryName());         // No I18N
        }
        return articleMap;
    }

    public HashMap<String, Object> getArticleMapObject(Resource article) {
        HashMap<String, Object> articleMap = new HashMap<>();
        if (article != null) {
            articleMap.put("id", article.getId());         // No I18N
            articleMap.put("name", article.getTitle());         // No I18N
            if (article.getStats() != null) {
                articleMap.put("likeCount", article.getStats().getLiked());         // No I18N
                articleMap.put("dislikeCount", article.getStats().getDisliked());         // No I18N
                articleMap.put("viewCount", article.getStats().getViewed());         // No I18N
            }
            if (article.getCategory() != null) {
                if (article.getCategory().getId() != null) {
                    articleMap.put("categoryID", article.getCategory().getId());         // No I18N
                }
                if (article.getCategory().getName() != null) {
                    articleMap.put("categoryName", article.getCategory().getName());         // No I18N
                }
            }
        }
        return articleMap;
    }

    public HashMap<String, Object> getVisitorInfoObject(SIQVisitor siqVisitor) {
        HashMap<String, Object> visitorInfoMap = new HashMap();
        if (siqVisitor.getName() != null) {
            visitorInfoMap.put("name", siqVisitor.getName());         // No I18N
        }
        if (siqVisitor.getEmail() != null) {
            visitorInfoMap.put("email", siqVisitor.getEmail());         // No I18N
        }
        if (siqVisitor.getPhone() != null) {
            visitorInfoMap.put("phone", siqVisitor.getPhone());         // No I18N
        }
        visitorInfoMap.put("numberOfChats",
                LiveChatUtil.getString(siqVisitor.getNumberOfChats()));         // No I18N
        if (siqVisitor.getCity() != null) {
            visitorInfoMap.put("city", siqVisitor.getCity());         // No I18N
        }
        if (siqVisitor.getIp() != null) {
            visitorInfoMap.put("ip", siqVisitor.getIp());         // No I18N
        }
        if (siqVisitor.getFirstVisitTime() != null) {
            Date firstVisitTime = siqVisitor.getFirstVisitTime();
            visitorInfoMap.put("firstVisitTime",
                    LiveChatUtil.getString(firstVisitTime.getTime()));         // No I18N
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
        visitorInfoMap.put("numberOfVisits",
                LiveChatUtil.getString(siqVisitor.getNumberOfVisits()));         // No I18N
        visitorInfoMap.put("noOfDaysVisited",
                LiveChatUtil.getString(siqVisitor.getNoOfDaysVisited()));         // No I18N
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

    public class ZohoSalesIQPluginListener implements SalesIQListener, SalesIQChatListener,
            SalesIQKnowledgeBaseListener, SalesIQActionListener {

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
        public void handleResourceOpened(@NonNull ZohoSalesIQ.ResourceType resourceType, @Nullable Resource resource) {
            if (resource != null) {
                JSONObject result = new JSONObject();
                try {
                    JSONObject jsonObject = getAsJSONObject(resource);
                    result.put("resource", jsonObject);
                    addResourceType(resourceType, result);
                    eventEmitter(EVENT_RESOURCE_OPENED, result);
                    eventEmitter(ARTICLE_OPENED, resource.getId());
                } catch (JSONException ignored) {}
            }
        }

        @Override
        public void handleResourceClosed(@NonNull ZohoSalesIQ.ResourceType resourceType, @Nullable Resource resource) {
            if (resource != null) {
                JSONObject result = new JSONObject();
                try {
                    result.put("resource", getAsJSONObject(resource));
                    addResourceType(resourceType, result);
                    eventEmitter(EVENT_RESOURCE_CLOSED, result);
                    eventEmitter(ARTICLE_CLOSED, resource.getId());
                } catch (JSONException ignored) {}
            }
        }

        @Override
        public void handleResourceLiked(@NonNull ZohoSalesIQ.ResourceType resourceType, @Nullable Resource resource) {
            if (resource != null) {
                JSONObject result = new JSONObject();
                try {
                    result.put("resource", getAsJSONObject(resource));
                    addResourceType(resourceType, result);
                    eventEmitter(EVENT_RESOURCE_LIKED, result);
                    eventEmitter(ARTICLE_LIKED, resource.getId());
                } catch (JSONException ignored) {}
            }
        }

        @Override
        public void handleResourceDisliked(@NonNull ZohoSalesIQ.ResourceType resourceType, @Nullable Resource resource) {
            if (resource != null) {
                JSONObject result = new JSONObject();
                try {
                    result.put("resource", getAsJSONObject(resource));
                    addResourceType(resourceType, result);
                    eventEmitter(EVENT_RESOURCE_DISLIKED, result);
                    eventEmitter(ARTICLE_DISLIKED, resource.getId());
                } catch (JSONException ignored) {}
            }
        }

        private JSONObject addResourceType(ZohoSalesIQ.ResourceType resourceType, JSONObject resource) {
            try {
                if (resourceType == ZohoSalesIQ.ResourceType.Articles) {
                    resource.put("type", KnowledgeBase.RESOURCE_ARTICLES);
                }
            } catch (JSONException e) {}
            return resource;
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
            visitorMap.put("triggerName", triggerName);
            visitorMap.put("visitorInformation", getVisitorInfoObject(visitor));
            Gson gson = new Gson();
            String json = gson.toJson(visitorMap);
            eventEmitter(CUSTOMTRIGGER, json);
        }

        @Override
        public void handleBotTrigger() {
            eventEmitter(BOT_TRIGGER, null);
        }

        @Override
        public void handleCustomLauncherVisibility(boolean visible) {
            SalesIQListener.super.handleCustomLauncherVisibility(visible);
            eventEmitter(HANDLE_CUSTOM_LAUNCHER_VISIBILITY, visible);
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
        public void handleCustomAction(SalesIQCustomAction salesIQCustomAction,
                                       final SalesIQCustomActionListener salesIQCustomActionListener) {
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

        @Override
        public boolean handleUri(Uri uri, VisitorChat visitorChat) {
            HashMap chatMap = getChatMapObject(visitorChat);
            // HashMap visitorMap = new HashMap<>();
            chatMap.put("url", uri.toString());
            // visitorMap.put("chat", chatMap);
            String json = new Gson().toJson(chatMap);
            eventEmitter(HANDLE_URL, json);
            return shouldOpenUrl;
        }
    }

    public void setLauncherPropertiesForAndroid(final JSONObject launcherPropertiesMap) {
        HANDLER.post(() -> {
            try {
                int mode = LauncherModes.FLOATING;
                if (launcherPropertiesMap.has("mode")) {
                    mode = launcherPropertiesMap.getInt("mode");
                }
                LauncherProperties launcherProperties = new LauncherProperties(mode);
                if (launcherPropertiesMap.has("x")) {
                    int x = launcherPropertiesMap.getInt("x");
                    if (x > -1) {
                        launcherProperties.setX(x);
                    }
                }
                if (launcherPropertiesMap.has("y")) {
                    int y = launcherPropertiesMap.getInt("y");
                    if (y > -1) {
                        launcherProperties.setY(y);
                    }
                }
                if (launcherPropertiesMap.has("horizontalDirection")) {
                    LauncherProperties.Horizontal horizontalDirection = null;
                    String horizontalDirectionValue = launcherPropertiesMap.getString("horizontalDirection");
                    if (LAUNCHER_HORIZONTAL_LEFT.equals(horizontalDirectionValue)) {
                        horizontalDirection = LauncherProperties.Horizontal.LEFT;
                    } else if (LAUNCHER_HORIZONTAL_RIGHT.equals(horizontalDirectionValue)) {
                        horizontalDirection = LauncherProperties.Horizontal.RIGHT;
                    }
                    if (horizontalDirection != null) {
                        launcherProperties.setDirection(horizontalDirection);
                    }
                }
                if (launcherPropertiesMap.has("verticalDirection")) {
                    LauncherProperties.Vertical verticalDirection = null;
                    String verticalDirectionValue = launcherPropertiesMap.getString("verticalDirection");
                    if (LAUNCHER_VERTICAL_TOP.equals(verticalDirectionValue)) {
                        verticalDirection = LauncherProperties.Vertical.TOP;
                    } else if (LAUNCHER_VERTICAL_BOTTOM.equals(verticalDirectionValue)) {
                        verticalDirection = LauncherProperties.Vertical.BOTTOM;
                    }
                    if (verticalDirection != null) {
                        launcherProperties.setDirection(verticalDirection);
                    }
                }
                if (launcherPropertiesMap.has("icon") && ZohoSalesIQ.getApplicationManager() != null && ZohoSalesIQ.getApplicationManager().getApplication() != null) {
                    try {
                        launcherProperties.setIcon(getDrawable(launcherPropertiesMap.getString("icon")));
                    } catch (JSONException ignored) {
                    }
                }
                ZohoSalesIQ.setLauncherProperties(launcherProperties);
            } catch (JSONException ignored) {
            }
        });
    }

    private Drawable getDrawable(String resourceName) {
        SalesIQApplicationManager salesIQApplicationManager = ZohoSalesIQ.getApplicationManager();
        int resourceId = getDrawableResourceId(resourceName);
        Drawable drawable = null;
        if (resourceId > 0 && salesIQApplicationManager != null && salesIQApplicationManager.getApplication() != null) {
            drawable = AppCompatResources.getDrawable(salesIQApplicationManager.getApplication().getApplicationContext(), resourceId);
        }
        return drawable;
    }

    public void setLauncherIconForAndroid(String resourceName) {
        Drawable drawable = getDrawable(resourceName);
        if (drawable != null) {
            ZohoSalesIQ.setLauncherIcon(drawable);
        }
    }

    public void refreshLauncher() {
        LauncherUtil.refreshLauncher();
    }

    public void sendEvent(final String event, final JSONArray objects) {
        HANDLER.post(() -> {
            try {
                switch (event) {
                    case EVENT_OPEN_URL:
                        final Application context = this.cordova.getActivity().getApplication();
                        if (!shouldOpenUrl && objects.length() == 1) {
                            LiveChatUtil.openUri(context, Uri.parse(objects.getString(0)));
                        }
                        break;
                    case EVENT_COMPLETE_CHAT_ACTION:
                        if (objects.length() > 0) {
                            String uuid = objects.getString(0);
                            boolean success = objects.length() <= 1 || objects.getBoolean(1);
                            String message = objects.length() == 3 ? objects.getString(2) : null;
                            if (uuid != null && !uuid.isEmpty()) {
                                SalesIQCustomActionListener listener = actionsList.get(uuid);
                                if (listener != null) {
                                    if (message != null && !message.isEmpty()) {
                                        if (success) {
                                            listener.onSuccess(message);
                                        } else {
                                            listener.onFailure(message);
                                        }
                                    } else {
                                        if (success) {
                                            listener.onSuccess();
                                        } else {
                                            listener.onFailure();
                                        }
                                    }
                                }
                                if (actionsList != null) {
                                    actionsList.remove(uuid);
                                }
                            }
                        }
                        break;
                }
            } catch (JSONException e) {
            }
        });
    }

    public void shouldOpenUrl(final boolean value) {
        shouldOpenUrl = value;
    }

    public void setTabOrder(final JSONArray tabNames) {
        /** @apiNote Please remove the -1 below when the {@link ZohoSalesIQ.Tab.FAQ} is removed */
        int minimumTabOrdersSize = Math.min(tabNames.length(), ZohoSalesIQ.Tab.values().length - 1);
        ZohoSalesIQ.Tab[] tabOrder = new ZohoSalesIQ.Tab[minimumTabOrdersSize];
        int insertIndex = 0;
        for (int index = 0; index < minimumTabOrdersSize; index++) {
            String tabName = null;
            try {
                tabName = tabNames.getString(index);
            } catch (JSONException ignored) {
            }
            if (Tab.CONVERSATIONS.name.equals(tabName)) {
                tabOrder[insertIndex++] = ZohoSalesIQ.Tab.Conversations;
            } else if (Tab.FAQ.name.equals(tabName) || Tab.KNOWLEDGE_BASE.name.equals(tabName)) {
                tabOrder[insertIndex++] = ZohoSalesIQ.Tab.KnowledgeBase;
            }
        }
        ZohoSalesIQ.setTabOrder(tabOrder);
    }

    public void printDebugLogsForAndroid(final Boolean value) {
        ZohoSalesIQ.printDebugLogs(value);
    }

    @SuppressLint("DiscouragedApi")
    private int getDrawableResourceId(String drawableName) {
        SalesIQApplicationManager salesIQApplicationManager = ZohoSalesIQ.getApplicationManager();
        int resourceId = 0;
        if (salesIQApplicationManager != null && salesIQApplicationManager.getApplication() != null) {
            resourceId = salesIQApplicationManager.getApplication().getResources().getIdentifier(
                    drawableName, "drawable",   // No I18N
                    salesIQApplicationManager.getApplication().getPackageName());

        }
        return resourceId;
    }

    @SuppressLint("DiscouragedApi")
    private int getStyleResourceId(String styleName) {
        SalesIQApplicationManager salesIQApplicationManager = ZohoSalesIQ.getApplicationManager();
        int resourceId = 0;
        if (salesIQApplicationManager != null && salesIQApplicationManager.getApplication() != null) {
            resourceId = salesIQApplicationManager.getApplication().getResources().getIdentifier(
                    styleName, "style",   // No I18N
                    salesIQApplicationManager.getApplication().getPackageName());

        }
        return resourceId;
    }

    void setNotificationIconForAndroid(final String drawableName) {
        int resourceId = getDrawableResourceId(drawableName);
        if (resourceId > 0) {
            ZohoSalesIQ.Notification.setIcon(resourceId);
        }
    }

    void setThemeForAndroid(final String name) {
        int resourceId = getStyleResourceId(name);
        if (resourceId > 0) {
            ZohoSalesIQ.setTheme(resourceId);
        }
    }

    void dismissUI() {
        ZohoSalesIQ.dismissUI();
    }

    void showFeedbackAfterSkip(final boolean enable) {
        ZohoSalesIQ.Chat.showFeedbackAfterSkip(enable);
    }

    void setLoggerEnabled(final boolean value) {
        ZohoSalesIQ.Logger.setEnabled(value);
    }

    void isLoggerEnabled(final CallbackContext callbackContext) {
        HANDLER.post(() -> callbackContext.success(ZohoSalesIQ.Logger.isEnabled() ? 1 : 0));
    }

    static class KnowledgeBase {

        static final String RESOURCE_ARTICLES = "RESOURCE_ARTICLES";    // No I18N

        private static @Nullable ZohoSalesIQ.ResourceType getResourceType(String value) {
            ZohoSalesIQ.ResourceType resourceType;
            if (RESOURCE_ARTICLES.equals(value)) {
                resourceType = ZohoSalesIQ.ResourceType.Articles;
            } else {
                resourceType = null;
            }
            return resourceType;
        }

        static void isKnowledgeBaseEnabled(final String type, final CallbackContext callbackContext) {
            executeIfResourceTypeIsValid(type, callbackContext, () -> callbackContext.success(LiveChatUtil.getString(ZohoSalesIQ.KnowledgeBase.isEnabled(getResourceType(type)))));
        }

        static void setKnowledgeBaseRecentlyViewedCount(final int limit) {
            ZohoSalesIQ.KnowledgeBase.setRecentlyViewedCount(limit);
        }


        static void setVisibility(final String type, final boolean shouldShow) {
            executeIfResourceTypeIsValid(type, null, () -> ZohoSalesIQ.KnowledgeBase.setVisibility(getResourceType(type), shouldShow));
        }

        static void categorize(final String type, final boolean shouldCategorize) {
            executeIfResourceTypeIsValid(type, null, () -> ZohoSalesIQ.KnowledgeBase.categorize(getResourceType(type), shouldCategorize));
        }

        static void combineDepartments(final String type, final boolean merge) {
            executeIfResourceTypeIsValid(type, null, () -> ZohoSalesIQ.KnowledgeBase.combineDepartments(getResourceType(type), merge));
        }

        //void  setRecentShowLimit(String type) {
        //   ZohoSalesIQ.KnowledgeBase.setRecentShowLimit(value)
        // }

        static void getResourceDepartments(final CallbackContext callbackContext) {
            ZohoSalesIQ.KnowledgeBase.getResourceDepartments(new ResourceDepartmentsListener() {
                @Override
                public void onSuccess(@NonNull List<ResourceDepartment> resourceDepartments) {
                    callbackContext.success(getAsJSONArray(resourceDepartments));
                }

                @Override
                public void onFailure(int code, @Nullable String message) {
                    callbackContext.error(getErrorJson(code, message));
                }
            });
        }

        static void open(final String type, final String id, final CallbackContext callbackContext) {
            executeIfResourceTypeIsValid(type, callbackContext, () -> ZohoSalesIQ.KnowledgeBase.open(getResourceType(type), id, new OpenResourceListener() {
                @Override
                public void onSuccess() {
                    callbackContext.success();
                }

                @Override
                public void onFailure(int code, @Nullable String message) {
                    callbackContext.error(getErrorJson(code, message));
                }
            }));
        }

        static void getSingleResource(final String type, final String id, final CallbackContext callbackContext) {
            executeIfResourceTypeIsValid(type, callbackContext, () -> ZohoSalesIQ.KnowledgeBase.getSingleResource(getResourceType(type), id, new ResourceListener() {
                @Override
                public void onSuccess(@Nullable Resource resource) {
                    callbackContext.success(getAsJSONObject(resource));
                }

                @Override
                public void onFailure(int code, @Nullable String message) {
                    callbackContext.error(getErrorJson(code, message));
                }
            }));
        }

        static void getResources(final String type, final String departmentID, final String parentCategoryID, final int page, final int limit, final String searchKey, final boolean includeChildCategoryResources, final CallbackContext callbackContext) {
            executeIfResourceTypeIsValid(type, callbackContext, () -> ZohoSalesIQ.KnowledgeBase.getResources(getResourceType(type), departmentID, parentCategoryID, searchKey, page, limit, includeChildCategoryResources, new ResourcesListener() {
                @Override
                public void onSuccess(@NonNull List<Resource> resources, boolean moreDataAvailable) {
                    JSONObject successJson = new JSONObject();
                    try {
                        successJson.put("resources", getAsJSONArray(resources));
                        successJson.put("moreDataAvailable", moreDataAvailable);
                    } catch (JSONException ignored) {}
                    callbackContext.success(successJson);
                }

                @Override
                public void onFailure(int code, @Nullable String message) {
                    callbackContext.error(getErrorJson(code, message));
                }
            }));
        }

        static void getCategories(final String type, final String departmentID, final String parentCategoryID, final CallbackContext callbackContext) {
            executeIfResourceTypeIsValid(type, callbackContext, () -> ZohoSalesIQ.KnowledgeBase.getCategories(getResourceType(type), departmentID, parentCategoryID, new ResourceCategoryListener() {
                @Override
                public void onSuccess(@NonNull List<ResourceCategory> resourceCategories) {
                    callbackContext.success(getAsJSONArray(resourceCategories));
                }

                @Override
                public void onFailure(int code, @Nullable String message) {
                    callbackContext.error(getErrorJson(code, message));
                }
            }));
        }

        private static void executeIfResourceTypeIsValid(String type, CallbackContext callbackContext, Runnable runnable) {
            ZohoSalesIQ.ResourceType resourceType = getResourceType(type);
            if (resourceType != null) {
                runnable.run();
            } else {
                if (callbackContext != null) {
                    callbackContext.error(getResourceTypeErrorMap());
                }
            }
        }

        static void handleKnowledgeBaseCalls(String action, JSONArray data, CallbackContext callbackContext) {
            try {
                if (action.equals("getKnowledgeBaseResourceDepartments")) {
                    KnowledgeBase.getResourceDepartments(callbackContext);
                } else if (action.equals("setKnowledgeBaseRecentlyViewedCount")) {
                    KnowledgeBase.setKnowledgeBaseRecentlyViewedCount(LiveChatUtil.getInteger(data.get(0)));
                } else {
                    String type = data.get(0).toString();
                    switch (action) {
                        case "isKnowledgeBaseEnabled":
                            KnowledgeBase.isKnowledgeBaseEnabled(type, callbackContext);
                            break;
                        case "setKnowledgeBaseVisibility":
                            KnowledgeBase.setVisibility(type, LiveChatUtil.getBoolean(data.get(1)));
                            break;
                        case "categorizeKnowledgeBase":
                            KnowledgeBase.categorize(type, LiveChatUtil.getBoolean(data.get(1)));
                            break;
                        case "combineKnowledgeBaseDepartments":
                            KnowledgeBase.combineDepartments(type, LiveChatUtil.getBoolean(data.get(1)));
                            break;
                        case "openKnowledgeBase":
                            KnowledgeBase.open(type, LiveChatUtil.getString(data.get(1)), callbackContext);
                            break;
                        case "getKnowledgeBaseSingleResource":
                            KnowledgeBase.getSingleResource(type, LiveChatUtil.getString(data.get(1)), callbackContext);
                            break;
                        case "getKnowledgeBaseResources":
                            KnowledgeBase.getResources(type, getStringOrNull(data.get(1)), getStringOrNull(data.get(2)), LiveChatUtil.getInteger(data.get(3)), LiveChatUtil.getInteger(data.get(4)), getStringOrNull(data.get(5)), false, callbackContext);
                            break;
                        case "getKnowledgeBaseCategories":
                            KnowledgeBase.getCategories(type, getStringOrNull(data.get(1)), getStringOrNull(data.get(2)), callbackContext);
                            break;
                    }
                }
            } catch (JSONException ignored) {}
        }

        private static @Nullable String getStringOrNull(Object object) {
            String value;
            if (object == null || object == JSONObject.NULL) {
                value = null;
            } else {
                value = (String) object;
            }
            return value;
        }

        static JSONObject getResourceTypeErrorMap() {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("code", 100);         // No I18N
                jsonObject.put("message", "Invalid resource type");         // No I18N
            } catch (JSONException ignored) {}
            return jsonObject;
        }
    }

    static JSONArray getAsJSONArray(Object object) {
        JSONArray finalJsonArray = new JSONArray();
        try {
            if (object != null) {
                Gson gson = DataModule.getGson();
                JsonArray jsonObjectList = JsonParser.parseString(gson.toJson(object)).getAsJsonArray();
                int size = jsonObjectList == null ? 0 : jsonObjectList.size();
                for (int index = 0; index < size; index++) {
                    finalJsonArray.put(getAsJSONObject(jsonObjectList.get(index)));
                }
            }
        } catch (Exception ignored) {}
        return finalJsonArray;
    }

    static JSONObject getAsJSONObject(Object object) {
        JSONObject jsonResult = new JSONObject();
        if (object != null) {
            JSONObject finalJsonObject;
            if (object instanceof JSONObject) {
                finalJsonObject = (JSONObject) object;
            } else {
                try {
                    finalJsonObject = new JSONObject(DataModule.getGson().toJson(object));
                } catch (Exception exception) {
                    finalJsonObject = GsonExtensionsKt.fromJsonSafe(DataModule.getGson(), DataModule.getGson().toJson(object), JSONObject.class);
                }
            }
            if (finalJsonObject != null) {
                for (Iterator<String> it = finalJsonObject.keys(); it.hasNext(); ) {
                    String key = it.next();
                    boolean hasNonValue = !finalJsonObject.isNull(key);
                    if (hasNonValue) {
                        try {
                            Object value = finalJsonObject.get(key);
                            if (value instanceof JSONObject) {
                                jsonResult.put(convertToCamelCase(key), getAsJSONObject(value));
                            } else {
                                jsonResult.put(convertToCamelCase(key), value);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        return jsonResult;
    }

    static String convertToCamelCase(String input) {
        StringBuilder camelCase = new StringBuilder(30);
        boolean capitalizeNext = false;
        if (input != null) {
            for (char c : input.toCharArray()) {
                if (c == '_') {
                    capitalizeNext = true;
                } else {
                    camelCase.append(capitalizeNext ? Character.toUpperCase(c) : c);
                    capitalizeNext = false;
                }
            }
        }
        return camelCase.toString();
    }

    static JSONObject getErrorJson(int code, String message) {
        JSONObject errorMap = new JSONObject();
        try {
            errorMap.put("code", code);         // No I18N
            errorMap.put("message", message);         // No I18N
        } catch (JSONException ignored) {}
        return errorMap;
    }

    // void clearLogsForiOS() {}

    // void setLoggerPathForiOS(final String value) {}

    // void writeLogForiOS(final String message, final String logLevel, final CallbackContext callback) {}
}