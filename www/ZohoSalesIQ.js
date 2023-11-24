var exec = require('cordova/exec');         // No I18N
var serviceName = "ZohoSalesIQPlugin";         // No I18N

//Event Types
exports.EVENT = {
    SUPPORT_OPENED: "SUPPORT_OPENED",   // No I18N
    SUPPORT_CLOSED: "SUPPORT_CLOSED",   // No I18N
    CHATVIEW_OPENED: "CHATVIEW_OPENED", // No I18N
    CHATVIEW_CLOSED: "CHATVIEW_CLOSED", // No I18N
    CHAT_OPENED: "CHAT_OPENED", // No I18N
    CHAT_QUEUE_POSITION_CHANGED: "CHAT_QUEUE_POSITION_CHANGED", // No I18N
    CHAT_CLOSED: "CHAT_CLOSED", // No I18N
    CHAT_MISSED: "CHAT_MISSED", // No I18N
    CHAT_ATTENDED: "CHAT_ATTENDED", // No I18N
    CHAT_REOPENED: "CHAT_REOPENED", // No I18N
    ARTICLE_OPENED: "ARTICLE_OPENED",   // No I18N
    ARTICLE_CLOSED: "ARTICLE_CLOSED",   // No I18N
    ARTICLE_LIKED: "ARTICLE_LIKED", // No I18N
    ARTICLE_DISLIKED: "ARTICLE_DISLIKED",   // No I18N
    OPERATORS_ONLINE: "OPERATORS_ONLINE",   // No I18N
    OPERATORS_OFFLINE: "OPERATORS_OFFLINE", // No I18N
    VISITOR_IPBLOCKED: "VISITOR_IPBLOCKED", // No I18N
    FEEDBACK_RECEIVED: "FEEDBACK_RECEIVED", // No I18N
    RATING_RECEIVED: "RATING_RECEIVED", // No I18N
    PERFORM_CHATACTION: "PERFORM_CHATACTION",   // No I18N
    CUSTOMTRIGGER: "CUSTOMTRIGGER", // No I18N
    BOT_TRIGGER: "BOT_TRIGGER", // No I18N
    HANDLE_URL: "HANDLE_URL",    // No I18N
    RESOURCE_OPENED: "RESOURCE_OPENED", // No I18N
    RESOURCE_CLOSED: "RESOURCE_CLOSED", // No I18N
    RESOURCE_LIKED: "RESOURCE_LIKED",   // No I18N
    RESOURCE_DISLIKED: "RESOURCE_DISLIKED", // No I18N
};

exports.Resource = {
    ARTICLES: "RESOURCE_ARTICLES"   // No I18N
}


exports.Launcher = {
    STATIC_MODE: 1,
    FLOATING_MODE: 2,
    HORIZONTAL_LEFT: "LAUNCHER_HORIZONTAL_LEFT",
    HORIZONTAL_RIGHT: "LAUNCHER_HORIZONTAL_RIGHT",
    VERTICAL_TOP: "LAUNCHER_VERTICAL_TOP",
    VERTICAL_BOTTOM: "LAUNCHER_VERTICAL_BOTTOM",

    setIconForAndroid: function (resourceName) {
        exec(null, null, serviceName, 'setLauncherIconForAndroid', [resourceName]);         // No I18N
    }
}

exports.Event = {
    OPEN_URL: "EVENT_OPEN_URL",
    COMPLETE_CHAT_ACTION: "EVENT_COMPLETE_CHAT_ACTION"
}

exports.Tab = {
    CONVERSATIONS: "TAB_CONVERSATIONS",
    KNOWLEDGE_BASE: 'TAB_KNOWLEDGE_BASE',   //No I18N
    FAQ: 'TAB_FAQ'  //No I18N
}

//Chat Types
exports.CHATTYPE = {
    OPEN: "OPEN",   // No I18N
    WAITING: "WAITING", // No I18N
    CONNECTED: "CONNECTED",                       // No I18N
    ENDED: "ENDED", // No I18N
    CLOSED: "CLOSED",   // No I18N
    MISSED: "MISSED"    // No I18N
};

// API Methods
exports.init = function (appKey, accessKey, success, error) {
    exec(success, error, serviceName, 'init', [appKey, accessKey]);         // No I18N
    setPlatform(getPluginPlatform());
};

exports.enableScreenshotOption = function (success, error) {
    exec(success, error, serviceName, 'enableScreenshotOption', []);         // No I18N
};

exports.disableScreenshotOption = function (success, error) {
    exec(success, error, serviceName, 'disableScreenshotOption', []);         // No I18N
};

exports.enablePreChatForms = function (success, error) {
    exec(success, error, serviceName, 'enablePreChatForms', []);         // No I18N
};

exports.disablePreChatForms = function (success, error) {
    exec(success, error, serviceName, 'disablePreChatForms', []);         // No I18N
};

exports.setVisitorNameVisibility = function (visible, success, error) {
    exec(success, error, serviceName, 'setVisitorNameVisibility', [visible]);         // No I18N
};

exports.setLanguage = function (languageCode, success, error) {
    exec(success, error, serviceName, 'setLanguage', [languageCode]);         // No I18N
};

exports.setDepartment = function (departmentName, success, error) {
    exec(success, error, serviceName, 'setDepartment', [departmentName]);         // No I18N
};

exports.setDepartments = function (departmentList, success, error) {
    exec(success, error, serviceName, 'setDepartments', [departmentList]);         // No I18N
};

exports.setOperatorEmail = function (email, success, error) {
    exec(success, error, serviceName, 'setOperatorEmail', [email]);         // No I18N
};

exports.showOperatorImageInLauncher = function (visible, success, error) {
    exec(success, error, serviceName, 'showOperatorImageInLauncher', [visible]);         // No I18N
};

exports.setChatTitle = function (title, success, error) {
    exec(success, error, serviceName, 'setChatTitle', [title]);         // No I18N
};

exports.showOperatorImageInChat = function (visible, success, error) {
    exec(success, error, serviceName, 'showOperatorImageInChat', [visible]);         // No I18N
};

exports.showLauncher = function (visible, success, error) {
    exec(success, error, serviceName, 'showLauncher', [visible]);         // No I18N
};

exports.setThemeColorforiOS = function (colorCodeHex, success, error) {
    exec(success, error, serviceName, 'setThemeColorforiOS', [colorCodeHex]);         // No I18N
};

exports.endChat = function (chatID, success, error) {
    exec(success, error, serviceName, 'endChat', [chatID]);         // No I18N
};

exports.showOfflineMessage = function (visible, success, error) {
    exec(success, error, serviceName, 'showOfflineMessage', [visible]);         // No I18N
};

exports.setFeedbackVisibility = function (visible, success, error) {
    exec(success, error, serviceName, 'setFeedbackVisibility', [visible]);         // No I18N
};

exports.setRatingVisibility = function (visible, success, error) {
    exec(success, error, serviceName, 'setRatingVisibility', [visible]);         // No I18N
};

exports.getChats = function (success, error) {
    exec(success, error, serviceName, 'getChats', []);         // No I18N
};

exports.getChatsWithFilter = function (type, success, error) {
    exec(success, error, serviceName, 'getChatsWithFilter', [type]);         // No I18N
};

exports.getDepartments = function (success, error) {
    exec(success, error, serviceName, 'getDepartments', []);         // No I18N
};

exports.show = function (success, error) {
    exec(success, error, serviceName, 'show', []);         // No I18N
};

exports.openNewChat = function (success, error) {
    exec(success, error, serviceName, 'openNewChat', []);         // No I18N
};

exports.openChatWithID = function (chatID, success, error) {
    exec(success, error, serviceName, 'openChatWithID', [chatID]);         // No I18N
};

exports.fetchAttenderImage = function (attenderID, fetchDefault, success, error) {
    exec(success, error, serviceName, 'fetchAttenderImage', [attenderID, fetchDefault]);         // No I18N
};

//CHAT ACTION APIS
exports.registerChatAction = function (name, success, error) {
    exec(success, error, serviceName, 'registerChatAction', [name]);         // No I18N
};

exports.unregisterChatAction = function (name, success, error) {
    exec(success, error, serviceName, 'unregisterChatAction', [name]);         // No I18N
};

exports.unregisterAllChatActions = function (success, error) {
    exec(success, error, serviceName, 'unregisterAllChatActions', []);         // No I18N
};

exports.setChatActionTimeout = function (timeout, success, error) {
    exec(success, error, serviceName, 'setChatActionTimeout', [timeout]);         // No I18N
};

exports.completeChatAction = function (actionuuid, success, error) {
    exec(success, error, serviceName, 'completeChatAction', [actionuuid]);         // No I18N
};

exports.completeChatActionWithMessage = function (actionuuid, successful, message, success, error) {
    exec(success, error, serviceName, 'completeChatActionWithMessage', [actionuuid, successful, message]);         // No I18N
};

//VISITOR APIS
exports.setVisitorName = function (name, success, error) {
    exec(success, error, serviceName, 'setVisitorName', [name]);         // No I18N
};

exports.setVisitorEmail = function (email, success, error) {
    exec(success, error, serviceName, 'setVisitorEmail', [email]);         // No I18N
};

exports.setVisitorContactNumber = function (contact, success, error) {
    exec(success, error, serviceName, 'setVisitorContactNumber', [contact]);         // No I18N
};

exports.setVisitorLocation = function (location, success, error) {
    exec(success, error, serviceName, 'setVisitorLocation', [location]);         // No I18N
};

exports.setVisitorAddInfo = function (key, value, success, error) {
    exec(success, error, serviceName, 'setVisitorAddInfo', [key, value]);         // No I18N
};

exports.setQuestion = function (question, success, error) {
    exec(success, error, serviceName, 'setQuestion', [question]);         // No I18N
};

exports.startChat = function (question, success, error) {
    exec(success, error, serviceName, 'startChat', [question]);         // No I18N
};

exports.registerVisitor = function (visitorUID, success, error) {
    exec(success, error, serviceName, 'registerVisitor', [visitorUID]);         // No I18N
};

exports.unregisterVisitor = function (success, error) {
    exec(success, error, serviceName, 'unregisterVisitor', []);         // No I18N
};

//TRACKING APIS
exports.setPageTitle = function (title, success, error) {
    exec(success, error, serviceName, 'setPageTitle', [title]);         // No I18N
};

exports.setCustomAction = function (actionName, success, error) {
    exec(success, error, serviceName, 'setCustomAction', [actionName]);         // No I18N
};

exports.performCustomAction = function (actionName, shouldOpenChatWindow = false, success, error) {
    exec(success, error, serviceName, 'performCustomAction', [actionName, shouldOpenChatWindow]);         // No I18N
};

//INAPPNOTIFICATION APIS
exports.enableInAppNotification = function (success, error) {
    exec(success, error, serviceName, 'enableInAppNotification', []);         // No I18N
};

exports.disableInAppNotification = function (success, error) {
    exec(success, error, serviceName, 'disableInAppNotification', []);         // No I18N
};

//CONVERSATIONN APIS
exports.setConversationListTitle = function (title, success, error) {
    exec(success, error, serviceName, 'setConversationListTitle', [title]);         // No I18N
};

exports.setConversationVisibility = function (visible, success, error) {
    exec(success, error, serviceName, 'setConversationVisibility', [visible]);         // No I18N
};

//FAQ APIS
exports.setFAQVisibility = function (visible, success, error) {
    exec(success, error, serviceName, 'setFAQVisibility', [visible]);         // No I18N
};

exports.setFAQVisibility = function (visible, success, error) {
    exec(success, error, serviceName, 'setFAQVisibility', [visible]);         // No I18N
};

exports.getArticles = function (success, error) {
    exec(success, error, serviceName, 'getArticles', []);         // No I18N
};

exports.getArticlesWithCategoryID = function (categortID, success, error) {
    exec(success, error, serviceName, 'getArticlesWithCategoryID', [categortID]);         // No I18N
};

exports.getCategories = function (success, error) {
    exec(success, error, serviceName, 'getCategories', []);         // No I18N
};

exports.openArticle = function (articleID, success, error) {
    exec(success, error, serviceName, 'openArticle', [articleID]);         // No I18N
};

// Deprecated
exports.syncThemeWithOS = function (sync, success, error) {
    exec(success, error, serviceName, 'syncThemeWithOS', [sync]);         // No I18N
};

exports.syncThemeWithOSForAndroid = function (sync, success, error) {
    exec(success, error, serviceName, 'syncThemeWithOSForAndroid', [sync]);         // No I18N
};

exports.isMultipleOpenChatRestricted = function (success, error) {
    exec(success, error, serviceName, 'isMultipleOpenChatRestricted', []);         // No I18N
};

//MARK:- EVENT HANDLER WORKING PROTOTYPE
var listeners = {};

exports.addEventListener = function (name, method) {
    var listener_list = listeners[name]
    if (!listener_list) {
        listener_list = [];
    }
    listener_list.push(method)
    listeners[name] = listener_list;
};

exports.removeEventListenersForEvent = function (name) {
    let listener_list = listeners[name]
    if (listener_list) {
        delete listeners[name];
    }
};

exports.removeAllEventListeners = function () {
    listeners = {};
};

//MARK:- Internal API to send events to listeners
exports.sendEventToJs = function (name, body) {
    if (name) {
        let listener_list = listeners[name];
        if (listener_list) {
            for (var i = 0; i < listener_list.length; i++) {
                if (body) {
                    listener_list[i](parseResult(body));
                } else {
                    listener_list[i]();
                }
            }
        }
    }
};

exports.sendEvent = function (name, ...values) {
    exec(null, null, serviceName, 'sendEvent', [name, values]);         // No I18N
}

exports.setLauncherPropertiesForAndroid = function (launcherPropertiesMap) {
    exec(null, null, serviceName, 'setLauncherPropertiesForAndroid', [launcherPropertiesMap]);         // No I18N
}

exports.printDebugLogsForAndroid = function (value) {
    exec(null, null, serviceName, 'printDebugLogsForAndroid', [value]);         // No I18N
}

exports.Chat = {
    shouldOpenUrl: function (value) {
        exec(null, null, serviceName, 'shouldOpenUrl', [value]);         // No I18N
    }
}

exports.setTabOrder = function (...tabNames) {
    exec(null, null, serviceName, 'setTabOrder', [tabNames]);         // No I18N
}

exports.setThemeForAndroid = function (name) {
    exec(null, null, serviceName, 'setThemeForAndroid', [name]);         // No I18N
}


exports.Notification = {
    setIconForAndroid: function (resourceName) {
        exec(null, null, serviceName, 'setNotificationIconForAndroid', [resourceName]);         // No I18N
    }
}


exports.Logger = {

    INFO: "INFO",
    WARNING: "WARNING",
    ERROR: "ERROR",

    setEnabled: function (value) {
        exec(null, null, serviceName, 'setLoggerEnabled', [value]);         // No I18N
    },
    isEnabled: function (success) {
        exec(success, null, serviceName, 'isLoggerEnabled', []);         // No I18N
    },
    setPathForiOS: function (url) {
        exec(null, null, serviceName, 'setLoggerPathForiOS', [url]);         // No I18N
    },
    clearLogsForiOS: function () {
        exec(null, null, serviceName, 'clearLogsForiOS', []);         // No I18N
    },
    writeLogForiOS: function (log, level, success, error) {
        exec(success, error, serviceName, 'writeLogForiOS', [log, level]);         // No I18N
    }
}

exports.KnowledgeBase = {
//    isEnabled: function (type, callback) {
//      RNZohoSalesIQ.isKnowledgeBaseEnabled(type, callback);
//    },
    setVisibility: function (type, shouldShow) {
      exec(null, null, serviceName, 'setKnowledgeBaseVisibility', [type, shouldShow]);  // No I18N
    },
    categorize: function (type, shouldCategorize) {
      exec(null, null, serviceName, 'categorizeKnowledgeBase',[type, shouldCategorize]);    // No I18N
    },
    combineDepartments: function (type, merge) {
      exec(null, null, serviceName, 'combineKnowledgeBaseDepartments', [type, merge]);  // No I18N
    },
    // setRecentShowLimit: function (value) {
    //   exec(null, null, serviceName, 'setKnowledgeBaseRecentShowLimit', [value]); // No I18N
    // },
    getResourceDepartments: function (success, error) {
      exec(success, error, serviceName, 'getKnowledgeBaseResourceDepartments', []); // No I18N
    },
    open: function (type, id, success, error) {
      exec(success, error, serviceName, 'openKnowledgeBase', [type, id]); // No I18N
    },
    getSingleResource: function (type, id, success, error) {
      exec(success, error, serviceName, 'getKnowledgeBaseSingleResource', [type, id]);    // No I18N
    },
    getResources: function (type, departmentId = null, parentCategoryId = null, page = 1, limit = 99, searchKey = null, success, error) {
      exec(success, error, serviceName, 'getKnowledgeBaseResources',[type, departmentId, parentCategoryId, page, limit, searchKey]);  // No I18N
    },
    getCategories: function (type, departmentId = null, parentCategoryId = null, success, error) {
      exec(success, error, serviceName, 'getKnowledgeBaseCategories',[type, departmentId, parentCategoryId]); // No I18N
    }
}

function setPlatform(platform) {
    exec(null, null, serviceName, 'setPlatform', [platform]);
}

function getPluginPlatform() {
    if (window.Ionic) {
        return "Ionic";     // No I18N
    } else if (window.PhoneGap || window.phonegap) {
        return "PhoneGap";  // No I18N
    } else {
        return "Cordova";   // No I18N
    }
}

function parseResult(input) {
    if (typeof input === 'string') {
        try {
            var json = JSON.parse(input);
            if (typeof json === 'object') {
                return json;
            } else {
                return input;
            }
        } catch (e) {
            return input;
        }
    } else {
        return input;
    }
};

window.addEventListener('orientationchange', function() {
    exec(null, null, serviceName, 'refreshLauncher', []);   // No I18N
});
