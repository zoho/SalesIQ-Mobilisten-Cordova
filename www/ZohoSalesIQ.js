var exec = require('cordova/exec');         // No I18N
var serviceName = "ZohoSalesIQPlugin";         // No I18N

//Event Types
exports.EVENT = {
    SUPPORT_OPENED: "SUPPORT_OPENED",   // No I18N
    SUPPORT_CLOSED: "SUPPORT_CLOSED",   // No I18N
    CHATVIEW_OPENED: "CHATVIEW_OPENED", // No I18N
    CHATVIEW_CLOSED: "CHATVIEW_CLOSED", // No I18N
    CHAT_OPENED: "CHAT_OPENED", // No I18N
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
    CUSTOMTRIGGER: "CUSTOMTRIGGER" // No I18N
};

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

exports.enableVoiceMessages = function (success, error) {
    exec(success, error, serviceName, 'enableVoiceMessages', []);         // No I18N
};

exports.disableVoiceMessages = function (success, error) {
    exec(success, error, serviceName, 'disableVoiceMessages', []);         // No I18N
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

exports.performCustomAction = function (actionName, success, error) {
    exec(success, error, serviceName, 'performCustomAction', [actionName]);         // No I18N
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

exports.syncThemeWithOS = function (sync, success, error) {
    exec(success, error, serviceName, 'syncThemeWithOS', [sync]);         // No I18N
};

//MARK:- EVENT HANDLER WORKING PROTOTYPE
var listeners = {};

exports.addEventListener = function(name, method) {
    var listener_list = listeners[name]
    if(!listener_list){
        listener_list = [];
    }
    listener_list.push(method)
    listeners[name] = listener_list;
};

exports.removeEventListenersForEvent = function(name) {
    let listener_list = listeners[name]
    if(listener_list){
        delete listeners[name];
    }
};

exports.removeAllEventListeners = function() {
    listeners = {};
};

//MARK:- Internal API to send events to listeners
exports.sendEvent = function(name, body){
    if(name){
        let listener_list = listeners[name];
        if(listener_list){
            for(var i = 0; i < listener_list.length; i++){
                if(body){
                    listener_list[i](parseResult(body));
                }else{
                    listener_list[i]();
                }
            }
        }
    }
};

function setPlatform(platform){
    exec(null, null, serviceName, 'setPlatform', [platform]);
}

function getPluginPlatform(){
    if(window.Ionic){
        return "Ionic";     // No I18N
    }else if(window.PhoneGap || window.phonegap){
        return "PhoneGap";  // No I18N
    }else{
        return "Cordova";   // No I18N
    }
}

function parseResult(input) {
    if(typeof input === 'string'){
        try {
            var json = JSON.parse(input);
            if(typeof json === 'object'){
                return json;
            }else{
                return input;
            }
        } catch (e) {
            return input;
        }
    }else{
        return input;
    }
};
