/********* ZohoSalesIQPlugin.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>
#import <WebKit/WebKit.h>
#import <Mobilisten/Mobilisten.h>

@interface ZohoSalesIQPlugin : CDVPlugin <ZohoSalesIQDelegate, ZohoSalesIQChatDelegate, ZohoSalesIQFAQDelegate, ZohoSalesIQKnowledgeBaseDelegate> {
    // Member variables go here.
}

@end

@implementation ZohoSalesIQPlugin

//MARK:- CHAT ACTIONS STORE
NSMutableDictionary<NSString *, SIQActionHandler *> *actionDictionary;

NSString *OPERATORS_OFFLINE = @"OPERATORS_OFFLINE";
NSString *OPERATORS_ONLINE = @"OPERATORS_ONLINE";
NSString *CHATVIEW_CLOSED = @"CHATVIEW_CLOSED";
NSString *CHATVIEW_OPENED = @"CHATVIEW_OPENED";
NSString *HOMEVIEW_CLOSED = @"HOMEVIEW_CLOSED";
NSString *HOMEVIEW_OPENED = @"HOMEVIEW_OPENED";
NSString *SUPPORT_OPENED = @"SUPPORT_OPENED";
NSString *SUPPORT_CLOSED = @"SUPPORT_CLOSED";

NSString *ARTICLE_OPENED = @"ARTICLE_OPENED";
NSString *ARTICLE_CLOSED = @"ARTICLE_CLOSED";
NSString *ARTICLE_LIKED = @"ARTICLE_LIKED";
NSString *ARTICLE_DISLIKED = @"ARTICLE_DISLIKED";

NSString *CHAT_ATTENDED = @"CHAT_ATTENDED";
NSString *CHAT_CLOSED = @"CHAT_CLOSED";
NSString *FEEDBACK_RECEIVED = @"FEEDBACK_RECEIVED";
NSString *CHAT_MISSED = @"CHAT_MISSED";
NSString *CHAT_OPENED = @"CHAT_OPENED";
NSString *CHAT_QUEUE_POSITION_CHANGED = @"CHAT_QUEUE_POSITION_CHANGED";
NSString *RATING_RECEIVED = @"RATING_RECEIVED";
NSString *CHAT_REOPENED = @"CHAT_REOPENED";
NSString *PERFORM_CHATACTION = @"PERFORM_CHATACTION";
NSString *CUSTOMTRIGGER = @"CUSTOMTRIGGER";
NSString *BOT_TRIGGER = @"BOT_TRIGGER";

NSString *UNREAD_COUNT_CHANGED = @"UNREAD_COUNT_CHANGED";
NSString *VISITOR_IPBLOCKED = @"VISITOR_IPBLOCKED";

//MARK:- CHAT TYPES
NSString *TYPE_OPEN = @"OPEN";
NSString *TYPE_TRIGGERED = @"TRIGGERED";
NSString *TYPE_PROACTIVE = @"PROACTIVE";
NSString *TYPE_WAITING = @"WAITING";
NSString *TYPE_CONNECTED = @"CONNECTED";
NSString *TYPE_MISSED = @"MISSED";
NSString *TYPE_CLOSED = @"CLOSED";
NSString *TYPE_ENDED = @"ENDED";

NSString *serviceName = @"ZohoSalesIQ";

NSString *INFO_LOG = @"INFO";
NSString *WARNING_LOG = @"WARNING";
NSString *ERROR_LOG = @"ERROR";
NSString *CONVERSATIONS = @"TAB_CONVERSATIONS";
NSString *FAQ = @"TAB_FAQ";
NSString *KNOWLEDGEBASE = @"TAB_KNOWLEDGE_BASE";
NSString *HANDLE_URL = @"HANDLE_URL";
NSString *EVENT_OPEN_URL = @"EVENT_OPEN_URL";
NSString *EVENT_COMPLETE_CHAT_ACTION = @"EVENT_COMPLETE_CHAT_ACTION";
NSString *RESOURCE_ARTICLES = @"RESOURCE_ARTICLES";

NSString *RESOURCE_OPENED = @"RESOURCE_OPENED";
NSString *RESOURCE_CLOSED = @"RESOURCE_CLOSED";
NSString *RESOURCE_LIKED = @"RESOURCE_LIKED";
NSString *RESOURCE_DISLIKED = @"RESOURCE_DISLIKED";

NSString *LAUNCHER_VISIBILITY_MODE_ALWAYS = @"LAUNCHER_VISIBILITY_MODE_ALWAYS";
NSString *LAUNCHER_VISIBILITY_MODE_NEVER = @"LAUNCHER_VISIBILITY_MODE_NEVER";
NSString *LAUNCHER_VISIBILITY_MODE_WHEN_ACTIVE_CHAT = @"LAUNCHER_VISIBILITY_MODE_WHEN_ACTIVE_CHAT";

NSString *HANDLE_CUSTOM_LAUNCHER_VISIBILITY = @"HANDLE_CUSTOM_LAUNCHER_VISIBILITY";

NSString *ACTION_SOURCE_APP = @"ACTION_SOURCE_APP";
NSString *ACTION_SOURCE_SDK = @"ACTION_SOURCE_SDK";
NSString *NOTIFICATION_CLICKED = @"NOTIFICATION_CLICKED";


bool handleURI = YES;


- (void)performAdditionalSetup{
    //MARK:- PERFORM ADDITIONAL SETUP HERE
    
    //Add calls to any native code/native Mobilisten API here.
    
}

- (void)init:(CDVInvokedUrlCommand*)command{
    NSString* appKey = [command.arguments objectAtIndex:0];
    NSString* accessKey = [command.arguments objectAtIndex:1];
    [ZohoSalesIQ setPlatformWithPlatform:@"Cordova"];
    [ZohoSalesIQ initWithAppKey:appKey accessKey:accessKey authProvider:NULL completion:^(id<SIQError> _Nullable error) {
        CDVPluginResult* pluginResult = nil;
        if (error == nil) {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:YES];
            [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
        } else {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsBool:NO];
            [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
    
    if(actionDictionary == nil){
        actionDictionary = [[NSMutableDictionary<NSString *, SIQActionHandler *> alloc] init];
    }
    
    ZohoSalesIQ.delegate = self;
    [ZohoSalesIQ Chat].delegate = self;
    [ZohoSalesIQ FAQ].delegate = self;
    [ZohoSalesIQ KnowledgeBase].delegate = self;
    [self performAdditionalSetup];
}

- (void)setPlatform:(CDVInvokedUrlCommand*)command{
    NSString* platform = [command.arguments objectAtIndex:0];
    if(platform!=nil){
        [ZohoSalesIQ setPlatformWithPlatform:platform];
    }
}

- (void)enableScreenshotOption:(CDVInvokedUrlCommand*)command{
    [[ZohoSalesIQ Chat] setVisibility:ChatComponentScreenshotOption visible:YES];
}

- (void)disableScreenshotOption:(CDVInvokedUrlCommand*)command{
    [[ZohoSalesIQ Chat] setVisibility:ChatComponentScreenshotOption visible:NO];
}

- (void)enablePreChatForms:(CDVInvokedUrlCommand*)command{
    [[ZohoSalesIQ Chat] setVisibility:ChatComponentPreChatForm visible:YES];
}

- (void)disablePreChatForms:(CDVInvokedUrlCommand*)command{
    [[ZohoSalesIQ Chat] setVisibility:ChatComponentPreChatForm visible:NO];
}

- (void)setVisitorNameVisibility:(CDVInvokedUrlCommand*)command{
    BOOL visible = [[command.arguments objectAtIndex:0] boolValue];
    [[ZohoSalesIQ Chat] setVisibility:ChatComponentVisitorName visible:visible];
}

//MARK:- CHAT APIS

- (void)setLanguage:(CDVInvokedUrlCommand*)command{
    NSString *language_code = [command.arguments objectAtIndex:0];
    if([language_code isEqualToString:@"en"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageEnglish];
    }else if([language_code isEqualToString:@"fr"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageFrench];
    }else if([language_code isEqualToString:@"de"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageGerman];
    }else if([language_code isEqualToString:@"es"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageSpanish];
    }else if([language_code isEqualToString:@"nl"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageDutch];
    }else if([language_code isEqualToString:@"no"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageNorwegian];
    }else if([language_code isEqualToString:@"tr"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageTurkish];
    }else if([language_code isEqualToString:@"ru"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageRussian];
    }else if([language_code isEqualToString:@"it"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageItalian];
    }else if([language_code isEqualToString:@"pt"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguagePortuguese];
    }else if([language_code isEqualToString:@"ko"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageKorean];
    }else if([language_code isEqualToString:@"ja"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageJapanese];
    }else if([language_code isEqualToString:@"da"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageDanish];
    }else if([language_code isEqualToString:@"pl"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguagePolish];
    }else if([language_code isEqualToString:@"ar"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageArabic];
    }else if([language_code isEqualToString:@"hu"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageHungarian];
    }else if([language_code isEqualToString:@"zh"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageChinese];
    }else if([language_code isEqualToString:@"he"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageHebrew];
    }else if([language_code isEqualToString:@"ga"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageIrish];
    }else if([language_code isEqualToString:@"ro"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageRomanian];
    }else if([language_code isEqualToString:@"th"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageThai];
    }else if([language_code isEqualToString:@"sv"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageSwedish];
    }else if([language_code isEqualToString:@"el"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageGreek];
    }else if([language_code isEqualToString:@"cs"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageCzech];
    }else if([language_code isEqualToString:@"sk"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageSlovak];
    }else if([language_code isEqualToString:@"sl"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageSlovenian];
    }else if([language_code isEqualToString:@"vi"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageVietnamese];
    }else if([language_code isEqualToString:@"fil"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageFilipino];
    }else if([language_code isEqualToString:@"fi"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageFinnish];
    }else if([language_code isEqualToString:@"zh_TW"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageChineseTraditional];
    }else if([language_code isEqualToString:@"hr"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageCroatian];
    }else if([language_code isEqualToString:@"zh_tw"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageChineseTraditional];
    }else if([language_code isEqualToString:@"bg"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageBulgarian];
    }else if([language_code isEqualToString:@"in"] || [language_code isEqualToString:@"id"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageIndonesian];
    }else if([language_code isEqualToString:@"ka"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageGeorgian];
    }else if([language_code isEqualToString:@"hy"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageArmenian];
    }else if([language_code isEqualToString:@"fa"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguagePersian];
    }else if([language_code isEqualToString:@"ta"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageTamil];
    }else if([language_code isEqualToString:@"kn"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageKannada];
    }else if([language_code isEqualToString:@"bn"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageBengali];
    }else if([language_code isEqualToString:@"hi"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageHindi];
    }else if([language_code isEqualToString:@"gu"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageGujarati];
    }else if([language_code isEqualToString:@"mr"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageMarathi];
    }else if([language_code isEqualToString:@"te"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageTelugu];
    }else if([language_code isEqualToString:@"pa"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguagePunjabi];
    }else if([language_code isEqualToString:@"or"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageOriya];
    }else if([language_code isEqualToString:@"ml"]){
        [[ZohoSalesIQ Chat] setLanguage:LanguageMalayalam];
    }else{
        [[ZohoSalesIQ Chat] setLanguage:LanguageEnglish];
    }
}

- (void)setDepartment:(CDVInvokedUrlCommand*)command{
    NSString *department = [command.arguments objectAtIndex:0];
    [[ZohoSalesIQ Chat] setDepartment:department];
}

- (void)setDepartments:(CDVInvokedUrlCommand*)command{
    NSArray<NSString *> *departments = [command.arguments objectAtIndex:0];
    if(departments!=nil){
        [[ZohoSalesIQ Chat] setDepartments:departments];
    }else{
        [[ZohoSalesIQ Chat] setDepartments:nil];
    }
}

- (void)setOperatorEmail:(CDVInvokedUrlCommand*)command{
    NSString *email = [command.arguments objectAtIndex:0];
    [[ZohoSalesIQ Chat] setAgentEmail:email];
}

- (void)showOperatorImageInLauncher:(CDVInvokedUrlCommand*)command{
    BOOL visible = [[command.arguments objectAtIndex:0] boolValue];
    [[ZohoSalesIQ Chat] setVisibility:ChatComponentAttenderImageOnLauncher visible:visible];
}

- (void)setChatTitle:(CDVInvokedUrlCommand*)command{
    NSString *title = [command.arguments objectAtIndex:0];
    [[ZohoSalesIQ Chat] setTitle:title];
}

- (void)showOperatorImageInChat:(CDVInvokedUrlCommand*)command{
    BOOL visible = [[command.arguments objectAtIndex:0] boolValue];
    [[ZohoSalesIQ Chat] setVisibility:ChatComponentAttenderImageInChat visible:visible];
}

- (void)showLauncher:(CDVInvokedUrlCommand*)command{
    BOOL visible = [[command.arguments objectAtIndex:0] boolValue];
    [ZohoSalesIQ showLauncher:visible];
}

- (void)setThemeColorforiOS:(CDVInvokedUrlCommand*)command{
    NSString *color_code = [command.arguments objectAtIndex:0];
    if(color_code != nil){
        unsigned rgbValue = 0;
        NSScanner *scanner = [NSScanner scannerWithString:color_code];
        [scanner setScanLocation:1];
        [scanner scanHexInt:&rgbValue];
        UIColor *themeColor = [UIColor colorWithRed:((rgbValue & 0xFF0000) >> 16)/255.0 green:((rgbValue & 0xFF00) >> 8)/255.0 blue:(rgbValue & 0xFF)/255.0 alpha:1.0];
        if(themeColor!=nil){
            SIQTheme *theme = [SIQTheme new];
            [theme setThemeColor:themeColor];
            [[ZohoSalesIQ Theme] setThemeWithTheme:theme];
            [[ZohoSalesIQ Chat] setThemeColor: themeColor];
        }
    }
}

- (void)setThemeColorforAndroid:(CDVInvokedUrlCommand*)command{
    // No Implementation
}

- (void)setThemeForAndroid:(CDVInvokedUrlCommand*)command{
    // No Implementation
}

- (void)syncThemeWithOSForAndroid:(CDVInvokedUrlCommand*)command{
    // No Implementation
}

- (void)isMultipleOpenChatRestricted:(CDVInvokedUrlCommand*)command{
    BOOL restricted = [[ZohoSalesIQ Chat] multipleOpenRestricted];
    CDVPluginResult* pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:restricted];
    [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)endChat:(CDVInvokedUrlCommand*)command{
    NSString *refID = [command.arguments objectAtIndex:0];
    if(refID != nil){
        [[ZohoSalesIQ Chat] endSessionWithReferenceID:refID];
    }
}

- (void)showOfflineMessage:(CDVInvokedUrlCommand*)command{
    BOOL visible = [[command.arguments objectAtIndex:0] boolValue];
    [[ZohoSalesIQ Chat] showOfflineMessage:visible];
}

- (void)setFeedbackVisibility:(CDVInvokedUrlCommand*)command{
    BOOL visible = [[command.arguments objectAtIndex:0] boolValue];
    [[ZohoSalesIQ Chat] setVisibility:ChatComponentFeedback visible:visible];
}

- (void)setRatingVisibility:(CDVInvokedUrlCommand*)command{
    BOOL visible = [[command.arguments objectAtIndex:0] boolValue];
    [[ZohoSalesIQ Chat] setVisibility:ChatComponentRating visible:visible];
}

- (void)getChats:(CDVInvokedUrlCommand*)command{
    ChatStatus chatStatus = ChatStatusAll;
    [[ZohoSalesIQ Chat] getListWithFilter:chatStatus completion:^(NSError * _Nullable error, NSArray<SIQVisitorChat *> * _Nullable chats) {
        CDVPluginResult* pluginResult = nil;
        if(error != nil){
            NSMutableDictionary *errorDictionary = [self getErrorObject:error];
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:errorDictionary];
            [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
        }else{
            NSMutableArray *chatsArray = [NSMutableArray array];
            chatsArray = [self getChatList:chats];
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsArray:chatsArray];
            [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
}

- (void)getChatsWithFilter:(CDVInvokedUrlCommand*)command{
    NSString *status = [command.arguments objectAtIndex:0];
    
    if(status != nil){
        ChatStatus chatStatus = ChatStatusAll;
        if([status isEqual: TYPE_OPEN]){
            chatStatus = ChatStatusOpen;
        }else if ([status  isEqual: TYPE_CONNECTED]){
            chatStatus = ChatStatusConnected;
        }else if ([status  isEqual: TYPE_WAITING]){
            chatStatus = ChatStatusWaiting;
        }else if ([status  isEqual: TYPE_MISSED]){
            chatStatus = ChatStatusMissed;
        }else if ([status  isEqual: TYPE_CLOSED]){
            chatStatus = ChatStatusClosed;
        }else if ([status  isEqual: TYPE_ENDED]){
            chatStatus = ChatStatusEnded;
        }else if ([status isEqual: TYPE_TRIGGERED]){
            chatStatus = ChatStatusTriggered;
        }else if ([status isEqual: TYPE_PROACTIVE]){
            chatStatus = ChatStatusProactive;
        }else{
            NSMutableDictionary *errorDictionary = [NSMutableDictionary dictionary];
            [errorDictionary setObject: @(604)  forKey: @"code"];
            [errorDictionary setObject: @"invalid filter type"  forKey: @"message"];
            CDVPluginResult* pluginResult = nil;
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:errorDictionary];
            [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
            
            return;
        }
        [[ZohoSalesIQ Chat] getListWithFilter:chatStatus completion:^(NSError * _Nullable error, NSArray<SIQVisitorChat *> * _Nullable chats) {
            CDVPluginResult* pluginResult = nil;
            if(error != nil){
                NSMutableDictionary *errorDictionary = [self getErrorObject:error];
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:errorDictionary];
                [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
            }else{
                NSMutableArray *chatsArray = [NSMutableArray array];
                chatsArray = [self getChatList:chats];
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsArray:chatsArray];
                [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
            }
        }];
    }
}

- (void)getDepartments:(CDVInvokedUrlCommand*)command{
    [[ZohoSalesIQ Chat] getDepartmentsWithCompletion:^(NSError * _Nullable error, NSArray<SIQDepartment *> * _Nullable departments) {
        CDVPluginResult* pluginResult = nil;
        if(error != nil){
            NSMutableDictionary *errorDictionary = [self getErrorObject:error];
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:errorDictionary];
            [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
        }else{
            NSMutableArray *departmentsArray = [NSMutableArray array];
            departmentsArray = [self getDepartmentList:departments];
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsArray:departmentsArray];
            [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
}

- (void)show:(CDVInvokedUrlCommand*)command{
    dispatch_async(dispatch_get_main_queue(), ^{
        [[ZohoSalesIQ Chat] showWithReferenceID:nil new:NO];
    });
}

- (void)openNewChat:(CDVInvokedUrlCommand*)command{
    dispatch_async(dispatch_get_main_queue(), ^{
        [[ZohoSalesIQ Chat] showWithReferenceID:nil new:YES];
    });
}

- (void)openChatWithID:(CDVInvokedUrlCommand*)command{
    NSString *refID = [command.arguments objectAtIndex:0];
    dispatch_async(dispatch_get_main_queue(), ^{
        [[ZohoSalesIQ Chat] showWithReferenceID:refID new:NO];
    });
}

- (void)fetchAttenderImage:(CDVInvokedUrlCommand*)command{
    NSString *attenderID = [command.arguments objectAtIndex:0];
    BOOL fetchDefault = [[command.arguments objectAtIndex:1] boolValue];
    
    SIQVisitorChat *chat = [SIQVisitorChat alloc];
    __block BOOL imageFetched = false;
    chat.attenderID = attenderID;
    [[ZohoSalesIQ Chat] fetchAttenderImageWithChat:chat fetchDefaultImage:fetchDefault completion:^(NSError * _Nullable error, UIImage * _Nullable image) {
        CDVPluginResult* pluginResult = nil;
        NSString *base64String = [UIImagePNGRepresentation(image) base64EncodedStringWithOptions:0];
        if(base64String == nil){ base64String = @""; }
        
        if(!imageFetched){
            imageFetched = true;
            if(error != nil){
                NSMutableDictionary *errorDictionary = [self getErrorObject:error];
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:errorDictionary];
                [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
            }else{
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:base64String];
                [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
            }
        }
    }];
    
}

- (void)setLoggerEnabled : (CDVInvokedUrlCommand*)enable{
    if ([enable.arguments count] > 0) {
        BOOL enableLog = [[enable.arguments objectAtIndex:0] boolValue];
        [[ZohoSalesIQ Logger] setEnabled:enableLog];
    }
}
- (void)clearLogsForiOS : (CDVInvokedUrlCommand*)command{
    [[ZohoSalesIQ Logger] clear];
}

- (void)isLoggerEnabled: (CDVInvokedUrlCommand*)command {
    BOOL logEnabled = [[ZohoSalesIQ Logger] isEnabled];
    CDVPluginResult* pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:logEnabled];
    [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
}


- (void)writeLogForiOS:(CDVInvokedUrlCommand*)command {
    if ([command.arguments count] > 1) {
        NSString* log = [command.arguments objectAtIndex:0];
        NSString* level = [command.arguments objectAtIndex:1];
        SIQDebugLogLevel debugLogLevel = SIQDebugLogLevelError;
        if([level isEqual: INFO_LOG]){
            debugLogLevel = SIQDebugLogLevelInfo;
        }else if ([level  isEqual: WARNING_LOG]){
            debugLogLevel = SIQDebugLogLevelWarning;
        }else if ([level  isEqual: ERROR_LOG]){
            debugLogLevel = SIQDebugLogLevelError;
        }
        [[ZohoSalesIQ Logger] write: log logLevel: debugLogLevel file:nil line:nil function:nil fileID:nil filePath:nil column:nil success:^(BOOL success) {
            CDVPluginResult* pluginResult = nil;
            if(success == true){
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:YES];
                [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
            }else{
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsBool:NO];
                [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
            }
        }];
    }
}

- (void)setLoggerPathForiOS : (CDVInvokedUrlCommand*)command {
    if ([command.arguments count] > 0) {
        NSString* path = [command.arguments objectAtIndex:0];
        NSMutableURLRequest *url = [[NSURL alloc] initWithString:path];
        [[ZohoSalesIQ Logger] setPath:url];
    }
}
//MARK:- SHOULD OPEN APIs
- (void)shouldOpenUrl : (CDVInvokedUrlCommand*)enable {
    if ([enable.arguments count] > 0) {
        BOOL enableHandleURI = [[enable.arguments objectAtIndex:0] boolValue];
        handleURI = enableHandleURI;
    }
}

- (BOOL)shouldOpenURL:(NSURL *)url in:(SIQVisitorChat * _Nullable)chat {
    NSMutableDictionary *chatDict = [NSMutableDictionary dictionary];
    chatDict = [self getChatObject:chat];
    [chatDict setObject: url.absoluteString  forKey: @"url"];
    [self sendEvent:HANDLE_URL body: chatDict];
    return handleURI;
}

//MARK:- EVENT HANDLE APIs
- (void)sendEvent: (CDVInvokedUrlCommand*)command {
    if ([command.arguments count] > 0) {
        NSString* eventName = [command.arguments objectAtIndex:0];
        if ([eventName  isEqual: EVENT_OPEN_URL]) {
            if ([command.arguments count] > 0) {
                NSArray *values = command.arguments[1];
                if (handleURI == NO) {
                    for (int i = 0; i < values.count; i++)
                    {
                        NSString *currentObject = [values objectAtIndex:i];
                        NSMutableURLRequest *url = [[NSURL alloc] initWithString:currentObject];
                        [ZohoSalesIQ openURL: url];
                        break;
                    }
                }
            }
        } else if ([eventName  isEqual: EVENT_COMPLETE_CHAT_ACTION]) {
            NSArray *values = command.arguments[1];
            NSString *uuid;
            BOOL complete;
            NSString *message;
            
            for (int i = 0; i < values.count; i++)
            {
                switch (i) {
                    case 0:
                        uuid = [values objectAtIndex: 0];
                        break;
                    case 1:
                        complete = [[values objectAtIndex:1] boolValue];
                    case 2:
                        message = [values objectAtIndex: 2];
                    default:
                        break;
                }
            }
            if (([actionDictionary valueForKey:uuid] != nil) && (uuid != nil)) {
                SIQActionHandler *handler = [actionDictionary valueForKey:uuid];
                if (message != nil) {
                    if (complete == YES){
                        [handler successWithMessage:message];
                    }else{
                        [handler faliureWithMessage:message];
                    }
                }else {
                    [handler successWithMessage:nil];
                }
                [actionDictionary removeObjectForKey:uuid];
            }
        }
    }
}

//MARK:- TAB ORDER API
- (void)setTabOrder:(CDVInvokedUrlCommand*)command {
    NSMutableArray *sendOrders = [[NSMutableArray alloc]init];
    if ([command.arguments count] > 0) {
        NSArray *tabs = command.arguments[0];
        for (int i = 0; i < [tabs count]; i++)
        {
            NSString  *currentObject = [tabs objectAtIndex:i];
            if ([currentObject  isEqual: CONVERSATIONS]) {
                [sendOrders addObject:[NSNumber numberWithInteger:0]];
            } else if ([currentObject  isEqual: FAQ] || [currentObject isEqual:KNOWLEDGEBASE]) {
                [sendOrders addObject:[NSNumber numberWithInteger:1]];
            }
        }
    }
    [ZohoSalesIQ setTabOrder: sendOrders];
}

//MARK:- CHAT ACTION APIs
- (void)registerChatAction:(CDVInvokedUrlCommand*)command{
    NSString *actionName = [command.arguments objectAtIndex:0];
    if(actionName!=nil){
        SIQChatAction *chatAction = [[SIQChatAction alloc] initWithName:actionName action:^(SIQChatActionArguments * _Nonnull arguments, SIQActionHandler * _Nonnull handler) {
            NSString *uuid = [[NSUUID UUID] UUIDString];
            [actionDictionary setObject:handler forKey:uuid];
            NSMutableDictionary *actionDetailsDictionary = [self getChatActionArguments:arguments withID:uuid actionName:actionName];
            [self sendEvent:PERFORM_CHATACTION body:actionDetailsDictionary];
        }];
        
        [[ZohoSalesIQ ChatActions] registerWithAction:chatAction];
    }
}

- (void)unregisterChatAction:(CDVInvokedUrlCommand*)command{
    NSString *actionName = [command.arguments objectAtIndex:0];
    if(actionName!=nil){
        [[ZohoSalesIQ ChatActions] unregisterWithNameWithName:actionName];
    }
}

- (void)unregisterAllChatActions:(CDVInvokedUrlCommand*)command{
    [[ZohoSalesIQ ChatActions] unregisterAll];
}

- (void)setChatActionTimeout:(CDVInvokedUrlCommand*)command{
    NSNumber *timeoutDuration = [command.arguments objectAtIndex:0];
    [[ZohoSalesIQ ChatActions] setTimeout:timeoutDuration.doubleValue];
}

- (void)completeChatAction:(CDVInvokedUrlCommand*)command{
    NSString *actionUUID = [command.arguments objectAtIndex:0];
    if(actionUUID!=nil){
        if([actionDictionary valueForKey:actionUUID] != nil){
            SIQActionHandler *handler = [actionDictionary valueForKey:actionUUID];
            [handler successWithMessage:nil];
            [actionDictionary removeObjectForKey:actionUUID];
        }
    }
}

- (void)completeChatActionWithMessage:(CDVInvokedUrlCommand*)command{
    NSString *actionUUID = [command.arguments objectAtIndex:0];
    BOOL complete = [[command.arguments objectAtIndex:1] boolValue];
    NSString *message = [command.arguments objectAtIndex:2];
    
    if(actionUUID != nil){
        if([actionDictionary valueForKey:actionUUID] != nil){
            SIQActionHandler *handler = [actionDictionary valueForKey:actionUUID];
            if(complete == true){
                [handler successWithMessage:message];
            }else{
                [handler faliureWithMessage:message];
            }
            [actionDictionary removeObjectForKey:actionUUID];
        }
    }
}


//MARK:- VISITOR APIs
- (void)setVisitorName:(CDVInvokedUrlCommand*)command{
    NSString *name = [command.arguments objectAtIndex:0];
    [[ZohoSalesIQ Visitor] setName:name];
}

- (void)setVisitorEmail:(CDVInvokedUrlCommand*)command{
    NSString *email = [command.arguments objectAtIndex:0];
    [[ZohoSalesIQ Visitor] setEmail:email];
}

- (void)setVisitorContactNumber:(CDVInvokedUrlCommand*)command{
    NSString *phone = [command.arguments objectAtIndex:0];
    [[ZohoSalesIQ Visitor] setContactNumber:phone];
}


- (void)setVisitorLocation:(CDVInvokedUrlCommand*)command{
    NSDictionary *location = [command.arguments objectAtIndex:0];
    
    if(location!= nil){
        SIQVisitorLocation *visitorLocation = [SIQVisitorLocation new];
        NSNumberFormatter *formatter = [[NSNumberFormatter alloc] init];
        formatter.numberStyle = NSNumberFormatterDecimalStyle;
        
        if([location valueForKey:@"latitude"]!=nil){
            //NSNumber *latitude = [f numberFromString:[location valueForKey:@"latitude"]];
            if([[location valueForKey:@"latitude"] isKindOfClass:[NSNumber class]]){
                [visitorLocation setLatitude:[location valueForKey:@"latitude"]];
            }
            if([[location valueForKey:@"latitude"] isKindOfClass:[NSString class]]){
                NSNumber *latitude = [formatter numberFromString:[location valueForKey:@"latitude"]];
                [visitorLocation setLatitude:latitude];
            }
        }
        
        if([location valueForKey:@"longitude"]!=nil){
            if([[location valueForKey:@"longitude"] isKindOfClass:[NSNumber class]]){
                [visitorLocation setLongitude:[location valueForKey:@"longitude"]];
            }
            if([[location valueForKey:@"longitude"] isKindOfClass:[NSString class]]){
                NSNumber *longitude = [formatter numberFromString:[location valueForKey:@"longitude"]];
                [visitorLocation setLongitude:longitude];
            }
        }
        
        if([location valueForKey:@"zipCode"]!=nil){
            if([[location valueForKey:@"zipCode"] isKindOfClass:[NSString class]]){
                [visitorLocation setZipCode:[location valueForKey:@"zipCode"]];
            }
        }
        
        if([location valueForKey:@"city"]!=nil){
            if([[location valueForKey:@"city"] isKindOfClass:[NSString class]]){
                [visitorLocation setCity:[location valueForKey:@"city"]];
            }
        }
        
        if([location valueForKey:@"state"]!=nil){
            if([[location valueForKey:@"state"] isKindOfClass:[NSString class]]){
                [visitorLocation setState:[location valueForKey:@"state"]];
            }
        }
        
        if([location valueForKey:@"country"]!=nil){
            if([[location valueForKey:@"country"] isKindOfClass:[NSString class]]){
                [visitorLocation setCountry:[location valueForKey:@"country"]];
            }
        }
        
        if([location valueForKey:@"countryCode"]!=nil){
            if([[location valueForKey:@"countryCode"] isKindOfClass:[NSString class]]){
                [visitorLocation setCountryCode:[location valueForKey:@"countryCode"]];
            }
        }
        
        [[ZohoSalesIQ Visitor] setLocation:visitorLocation];
        
    }
    
}

- (void)setVisitorAddInfo:(CDVInvokedUrlCommand*)command{
    NSString *key = [command.arguments objectAtIndex:0];
    NSString *value = [command.arguments objectAtIndex:1];
    if(key!=nil){
        if(value!=nil){
            [[ZohoSalesIQ Visitor] addInfo:key value:value];
        }
    }
}

- (void)setQuestion:(CDVInvokedUrlCommand*)command{
    NSString *question = [command.arguments objectAtIndex:0];
    if(question!=nil){
        [[ZohoSalesIQ Visitor] setQuestion:question];
    }
}

- (void)startChat:(CDVInvokedUrlCommand*)command{
    NSString *question = [command.arguments objectAtIndex:0];
    if(question!=nil){
        [[ZohoSalesIQ Chat] startChatWithQuestion:question];
    }
}

- (void)registerVisitor:(CDVInvokedUrlCommand*)command{
    NSString *visitorID = [command.arguments objectAtIndex:0];
    if(visitorID!=nil){
        [ZohoSalesIQ registerVisitor:visitorID completion:^(BOOL success) {
            CDVPluginResult* pluginResult = nil;
            if(success == true){
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:YES];
                [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
            }else{
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsBool:NO];
                [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
            }
        }];
    }
}

- (void)unregisterVisitor:(CDVInvokedUrlCommand*)command{
    [ZohoSalesIQ unregisterVisitorWithCompletion:^(BOOL success) {
        CDVPluginResult* pluginResult = nil;
        if(success == true){
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:YES];
            [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
        }else{
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsBool:NO];
            [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
}

//MARK:- VISITOR TRACKING APIS
- (void)setPageTitle:(CDVInvokedUrlCommand*)command{
    NSString *pageTitle = [command.arguments objectAtIndex:0];
    if(pageTitle!=nil){
        [[ZohoSalesIQ Tracking] setPageTitle:pageTitle];
    }
}

- (void)setCustomAction:(CDVInvokedUrlCommand*)command{
    NSString *customActionName = [command.arguments objectAtIndex:0];
    if(customActionName!=nil){
        [[ZohoSalesIQ Tracking] setCustomAction:customActionName shouldOpenChatWindow:NO];
    }
}

- (void)performCustomAction:(CDVInvokedUrlCommand*)command{
    NSString *customActionName = [command.arguments objectAtIndex:0];
    BOOL shouldOpen = [[command.arguments objectAtIndex:1] boolValue];

    if(customActionName!=nil){
        [[ZohoSalesIQ Visitor] performCustomAction:customActionName shouldOpenChatWindow:shouldOpen];
    }
}



//MARK:- IN-APP-NOTIFICATIONS APIS
- (void)enableInAppNotification:(CDVInvokedUrlCommand*)command{
    [[ZohoSalesIQ Chat] setVisibility:ChatComponentInAppNotifications visible:YES];
}

- (void)disableInAppNotification:(CDVInvokedUrlCommand*)command{
    [[ZohoSalesIQ Chat] setVisibility:ChatComponentInAppNotifications visible:NO];
}

//MARK:- CONVERSATION APIS
- (void)setConversationListTitle:(CDVInvokedUrlCommand*)command{
    NSString *listTitle = [command.arguments objectAtIndex:0];
    if(listTitle!=nil){
        [[ZohoSalesIQ Conversation] setTitle:listTitle];
    }
}

- (void)setConversationVisibility:(CDVInvokedUrlCommand*)command{
    BOOL visible = [[command.arguments objectAtIndex:0] boolValue];
    [[ZohoSalesIQ Conversation] setVisibility:visible];
}

//MARK:- FAQ APIS
- (void)setFAQVisibility:(CDVInvokedUrlCommand*)command{
    BOOL visible = [[command.arguments objectAtIndex:0] boolValue];
    [[ZohoSalesIQ FAQ] setVisibility:visible];
}

- (void)getArticles:(CDVInvokedUrlCommand*)command{
    [[ZohoSalesIQ FAQ] getArticlesWithCategoryID:nil :^(NSError * _Nullable error, NSArray<SIQFAQArticle *> * _Nullable articles) {
        CDVPluginResult* pluginResult = nil;
        if(error != nil){
            NSMutableDictionary *errorDictionary = [self getErrorObject:error];
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:errorDictionary];
            [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
        }else{
            NSMutableArray *articleArray = [NSMutableArray array];
            articleArray = [self getArticlesList:articles];
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsArray:articleArray];
            [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
}

- (void)getArticlesWithCategoryID:(CDVInvokedUrlCommand*)command{
    NSString *categoryID = [command.arguments objectAtIndex:0];
    [[ZohoSalesIQ FAQ] getArticlesWithCategoryID:categoryID :^(NSError * _Nullable error, NSArray<SIQFAQArticle *> * _Nullable articles) {
        CDVPluginResult* pluginResult = nil;
        if(error != nil){
            NSMutableDictionary *errorDictionary = [self getErrorObject:error];
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:errorDictionary];
            [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
        }else{
            NSMutableArray *articleArray = [NSMutableArray array];
            articleArray = [self getArticlesList:articles];
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsArray:articleArray];
            [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
}

- (void)getCategories:(CDVInvokedUrlCommand*)command{
    [[ZohoSalesIQ FAQ] getCategoriesWithDepartmentIDS:nil :^(NSError * _Nullable error, NSArray<SIQFAQCategory *> * _Nullable categories) {
        CDVPluginResult* pluginResult = nil;
        if(error != nil){
            NSMutableDictionary *errorDictionary = [self getErrorObject:error];
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:errorDictionary];
            [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
        }else{
            NSMutableArray *categoryArray = [NSMutableArray array];
            categoryArray = [self getFAQCategoryList:categories];
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsArray:categoryArray];
            [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
}

- (void)openArticle:(CDVInvokedUrlCommand*)command{
    NSString *articleID = [command.arguments objectAtIndex:0];
    if(articleID!=nil){
        [[ZohoSalesIQ FAQ] openArticleWithArticleID:articleID :^(NSError * _Nullable error)  {
            CDVPluginResult* pluginResult = nil;
            if(error != nil){
                NSMutableDictionary *errorDictionary = [self getErrorObject:error];
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:errorDictionary];
                [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
            }else{
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
                [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
            }
        }];
    }
}

- (void)dismissUI:(CDVInvokedUrlCommand*)command{
    [ZohoSalesIQ dismissUI];
}

- (void)hideQueueTime:(CDVInvokedUrlCommand*)command {
    BOOL hide = [[command.arguments objectAtIndex:0] boolValue];
    [[ZohoSalesIQ Chat] hideQueueTime:hide];
}


- (void)registerLocalizationFile:(CDVInvokedUrlCommand*)command {
    NSString *fileName = [command.arguments objectAtIndex:0];
    if(fileName != nil){
        [ZohoSalesIQ registerLocalizationFileWith:fileName];
    }
}

- (void)setNotificationActionSource:(CDVInvokedUrlCommand*)command {
    NSString *action = [command.arguments objectAtIndex:0];
    if([action isEqual: ACTION_SOURCE_APP]){
        [[ZohoSalesIQ Notification] setActionWith: ActionSourceApp];
    }else if ([action  isEqual: ACTION_SOURCE_SDK]){
        [[ZohoSalesIQ Notification] setActionWith: ActionSourceSdk];
    }
}

- (void)isSDKMessage:(CDVInvokedUrlCommand*)command {
    NSDictionary *payload = [command.arguments objectAtIndex:0];
    CDVPluginResult* pluginResult = nil;
    if ([ZohoSalesIQ isMobilistenNotification: payload]) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:YES];
        [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:NO];
        [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
    }
}

- (void)getNotificationPayload:(CDVInvokedUrlCommand*)command {
    NSDictionary *payload = [command.arguments objectAtIndex:0];
    [[ZohoSalesIQ Notification] getPayload:payload completion:^(SalesIQNotificationPayload * _Nullable completionObject) {
        NSMutableDictionary *resultMap = [NSMutableDictionary dictionary];
        CDVPluginResult* pluginResult = nil;
        if (completionObject != nil) {
            if ([completionObject isKindOfClass:[SalesIQChatNotificationPayload class]]) {
                SalesIQChatNotificationPayload *chatPayload = (SalesIQChatNotificationPayload *)completionObject;
                resultMap[@"type"] = @"chat";
                resultMap[@"payload"] = [chatPayload toDictionary];
            } else if ([completionObject isKindOfClass:[SalesIQEndChatNotificationPayload class]]) {
                SalesIQEndChatNotificationPayload *endChatPayload = (SalesIQEndChatNotificationPayload *)completionObject;
                resultMap[@"type"] = @"endChatDetails";
                resultMap[@"payload"] = [endChatPayload toDictionary];
            } else if ([completionObject isKindOfClass:[SalesIQVisitorHistoryNotificationPayload class]]) {
                SalesIQVisitorHistoryNotificationPayload *visitorHistoryPayload = (SalesIQVisitorHistoryNotificationPayload *)completionObject;
                resultMap[@"type"] = @"visitorHistory";
                resultMap[@"payload"] = [visitorHistoryPayload toDictionary];
            }
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:resultMap];
            [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
        } else {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:resultMap];
            [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
}

//MARK:- KNOWLEGEBASE APIs
- (void)isKnowledgeBaseEnabled: (CDVInvokedUrlCommand*)command {
    NSString *type = [command.arguments objectAtIndex:0];
    if ([type isEqualToString: RESOURCE_ARTICLES]) {
        BOOL knowledgebaseEnabled = [[ZohoSalesIQ KnowledgeBase] isEnabled: SIQResourceTypeArticles];
        CDVPluginResult* pluginResult = nil;
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:knowledgebaseEnabled];
        [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
    }
}

- (void)setKnowledgeBaseRecentlyViewedCount:(CDVInvokedUrlCommand*)command{
    NSInteger limit = [[command.arguments objectAtIndex:0] integerValue];
    [[ZohoSalesIQ KnowledgeBase] setRecentShowLimit:limit];
}

- (void)setKnowledgeBaseVisibility:(CDVInvokedUrlCommand*)command{
    NSString *type = [command.arguments objectAtIndex:0];
    BOOL enable = [[command.arguments objectAtIndex:1] boolValue];
    if ([type isEqualToString: RESOURCE_ARTICLES]) {
        [[ZohoSalesIQ KnowledgeBase] setVisibility:SIQResourceTypeArticles enable: enable];
    }
}

- (void)categorizeKnowledgeBase:(CDVInvokedUrlCommand*)command{
    NSString *type = [command.arguments objectAtIndex:0];
    BOOL enable = [[command.arguments objectAtIndex:1] boolValue];
    if ([type isEqualToString: RESOURCE_ARTICLES]) {
        [[ZohoSalesIQ KnowledgeBase] categorize:SIQResourceTypeArticles enable:enable];
    }
}

- (void)combineKnowledgeBaseDepartments:(CDVInvokedUrlCommand*)command{
    NSString *type = [command.arguments objectAtIndex:0];
    BOOL enable = [[command.arguments objectAtIndex:1] boolValue];
    if ([type isEqualToString: RESOURCE_ARTICLES]) {
        [[ZohoSalesIQ KnowledgeBase] combineDepartments:SIQResourceTypeArticles enable:enable];
    }
}

- (void)getKnowledgeBaseResourceDepartments:(CDVInvokedUrlCommand*)command{
    [[ZohoSalesIQ KnowledgeBase] getResourceDepartmentsWithCompletion:^(id<SIQError> _Nullable error, NSArray<SIQResourceDepartment *> * _Nullable departments) {
        CDVPluginResult* pluginResult = nil;
        if (departments != nil) {
            NSMutableArray *departmentsArray = [NSMutableArray array];
            for (SIQResourceDepartment *department in departments) {
                NSMutableDictionary *departmentDict = [NSMutableDictionary dictionary];
                departmentDict[@"id"] = department.id;
                departmentDict[@"name"] = department.name;
                [departmentsArray addObject:departmentDict];
            }
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsArray:departmentsArray];
            [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
        } else {
            NSMutableDictionary *errorDictionary = [self getSIQErrorObject:error];
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:errorDictionary];
            [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
}

- (void)openKnowledgeBase:(CDVInvokedUrlCommand*)command{
    NSString *type = [command.arguments objectAtIndex:0];
    NSString *articleID = [command.arguments objectAtIndex:1];

    if (type != nil && articleID != nil && ![articleID isKindOfClass:[NSNull class]]) {
        if ([type isEqualToString: RESOURCE_ARTICLES]) {
            [[ZohoSalesIQ KnowledgeBase] open:SIQResourceTypeArticles id:articleID completion:^(BOOL success, id<SIQError> _Nullable error) {
                if (error != nil) {
                    NSMutableDictionary *errorDictionary = [self getSIQErrorObject:error];
                    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:errorDictionary];
                    [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
                } else {
                    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:YES];
                    [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
                }
            }];
        }
    }
}

- (void)getKnowledgeBaseSingleResource:(CDVInvokedUrlCommand*)command{
    NSString *type = [command.arguments objectAtIndex:0];
    NSString *articleID = [command.arguments objectAtIndex:1];

    if (type != nil && articleID != nil && ![articleID isKindOfClass:[NSNull class]]) {
        if ([type isEqualToString: RESOURCE_ARTICLES]) {
            [[ZohoSalesIQ KnowledgeBase]getSingleResource:SIQResourceTypeArticles id:articleID completion:^(BOOL success, id<SIQError> _Nullable error, SIQKnowledgeBaseResource * _Nullable resource) {
                CDVPluginResult* pluginResult = nil;
                if (error != nil) {
                    NSMutableDictionary *errorDictionary = [self getSIQErrorObject:error];
                    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:errorDictionary];
                    [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
                } else {
                    NSMutableArray *resourceObject = [self getResourceObject:resource];
                    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsArray:resourceObject];
                    [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
                }
            }];
        }
    }
}

- (void)getKnowledgeBaseResources:(CDVInvokedUrlCommand*)command{
    NSString *type = [command.arguments objectAtIndex:0];
    NSString *departmentId = [command.arguments objectAtIndex:1];
    NSString *parentCategoryId = [command.arguments objectAtIndex:2];
    int page = [[command.arguments objectAtIndex:3] intValue];
    int limit = [[command.arguments objectAtIndex:4] intValue];
    NSString *searchKey = [command.arguments objectAtIndex:5];
    
    if ([departmentId isKindOfClass:[NSNull class]]) {
        departmentId = NULL;
    }
    if ([parentCategoryId isKindOfClass:[NSNull class]]) {
        parentCategoryId = NULL;
    }
    if ([searchKey isKindOfClass:[NSNull class]]) {
        searchKey = NULL;
    }
    
    if ([type isEqualToString: RESOURCE_ARTICLES]) {
        [[ZohoSalesIQ KnowledgeBase] getResources:SIQResourceTypeArticles departmentId:departmentId parentCategoryId:parentCategoryId searchKey:searchKey page:page limit:limit completion:^(BOOL success, id<SIQError> _Nullable error, NSArray<SIQKnowledgeBaseResource *> * _Nullable resources, BOOL moreDataAvailable) {
            CDVPluginResult* pluginResult = nil;
            NSNumber *available = [NSNumber numberWithBool:moreDataAvailable];
            if (error != nil) {
                NSMutableDictionary *errorDictionary = [self getSIQErrorObject:error];
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:errorDictionary];
                [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
            } else {
                NSMutableArray *resourceObject = [self getResourceList:resources];
                NSDictionary *resultDict = @{@"resources": resourceObject, @"moreDataAvailable": available};
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:resultDict];
                [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
            }
        }];
    }
}

- (void)getKnowledgeBaseCategories:(CDVInvokedUrlCommand*)command {
    NSString *type = [command.arguments objectAtIndex:0];
    NSString *departmentId = [command.arguments objectAtIndex:1];
    NSString *parentCategoryId = [command.arguments objectAtIndex:2];
    
    if ([departmentId isKindOfClass:[NSNull class]]) {
        departmentId = NULL;
    }
    if ([parentCategoryId isKindOfClass:[NSNull class]]) {
        parentCategoryId = NULL;
    }
    if ([type isEqualToString: RESOURCE_ARTICLES]) {
        [[ZohoSalesIQ KnowledgeBase] getCategories:SIQResourceTypeArticles departmentId:departmentId parentCategoryId:parentCategoryId completion:^(BOOL success, id<SIQError> _Nullable error, NSArray<SIQKnowledgeBaseCategory *> * _Nullable categories) {
            CDVPluginResult* pluginResult = nil;
            if (error != nil) {
                NSMutableDictionary *errorDictionary = [self getSIQErrorObject:error];
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:errorDictionary];
                [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
            } else {
                NSMutableArray *resourceObject = [self getCategoryList:categories];
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsArray:resourceObject];
                [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
            }
        }];
    }
}

//MARK:- Feedback APIs
- (void)showChatFeedbackAfterSkip:(CDVInvokedUrlCommand*)command{
    BOOL skip = [[command.arguments objectAtIndex:0] boolValue];
    [[ZohoSalesIQ Chat] showFeedbackAfterSkip:skip];
}

- (void)showChatFeedbackUpTo:(CDVInvokedUrlCommand*)command{
    NSInteger duration = [[command.arguments objectAtIndex:0] integerValue];
    [[ZohoSalesIQ Chat] showFeedbackWithUptoDuration: duration];
}

- (void)isChatEnabled:(CDVInvokedUrlCommand*)command{
    BOOL chatEnabled = [[ZohoSalesIQ Chat] isEnabled];
    CDVPluginResult* pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:chatEnabled];
    [[self commandDelegate] sendPluginResult:pluginResult callbackId:command.callbackId];
}

//MARK:- Launcher APIs
- (void)setLauncherVisibilityMode:(CDVInvokedUrlCommand*)command{
    NSString *mode = [command.arguments objectAtIndex:0];
    if ([mode isEqualToString: LAUNCHER_VISIBILITY_MODE_ALWAYS]) {
        [[ZohoSalesIQ Launcher] show:VisibilityModeAlways];
    } else if ([mode isEqualToString: LAUNCHER_VISIBILITY_MODE_NEVER]) {
        [[ZohoSalesIQ Launcher] show:VisibilityModeNever];
    } else if ([mode isEqualToString: LAUNCHER_VISIBILITY_MODE_WHEN_ACTIVE_CHAT]) {
        [[ZohoSalesIQ Launcher] show:VisibilityModeWhenActiveChat];
    }
}

- (void)setVisibilityModeToCustomLauncher:(CDVInvokedUrlCommand*)command{
    NSString *mode = [command.arguments objectAtIndex:0];
    if ([mode isEqualToString: LAUNCHER_VISIBILITY_MODE_ALWAYS]) {
        [[ZohoSalesIQ Launcher] setVisibilityModeToCustomLauncher:VisibilityModeAlways];
    } else if ([mode isEqualToString: LAUNCHER_VISIBILITY_MODE_NEVER]) {
        [[ZohoSalesIQ Launcher] setVisibilityModeToCustomLauncher:VisibilityModeNever];
    } else if ([mode isEqualToString: LAUNCHER_VISIBILITY_MODE_WHEN_ACTIVE_CHAT]) {
        [[ZohoSalesIQ Launcher] setVisibilityModeToCustomLauncher:VisibilityModeWhenActiveChat];
    }
}

- (void)enableLauncherDragToDismiss:(CDVInvokedUrlCommand*)command{
    BOOL enable = [[command.arguments objectAtIndex:0] boolValue];
    [[ZohoSalesIQ Launcher] enableDragToDismiss:enable];
}


//MARK:- INTERNAL HELPER METHODS

void mainThread(void (^block)(void)) {
    if ([NSThread isMainThread]) {
        block();
    } else {
        dispatch_sync(dispatch_get_main_queue(), block);
    }
}

- (void)stringByEvaluatingJavaScriptFromString:(NSString *)script {
#if WK_WEB_VIEW_ONLY
    if ([self.webView isKindOfClass:WKWebView.class]) {
        mainThread(^{
            [((WKWebView *)self.webView) evaluateJavaScript:script completionHandler:^(id resultID, NSError *error) {
                
            }];
        });
    }
#else
    if ([self.webView isKindOfClass:UIWebView.class]) {
        mainThread(^{
            [(UIWebView *)self.webView stringByEvaluatingJavaScriptFromString:script];
        });
    } else if ([self.webView isKindOfClass:WKWebView.class]) {
        mainThread(^{
            [((WKWebView *)self.webView) evaluateJavaScript:script completionHandler:^(id resultID, NSError *error) {
                
            }];
        });;
    }
#endif
}

- (void)sendEvent:(NSString*)name body:(id)body {
    if ([body isKindOfClass:[NSDictionary class]]) {
        body = [[NSString alloc] initWithData:[NSJSONSerialization dataWithJSONObject:body options:0 error:NULL] encoding:NSUTF8StringEncoding];
    }
    NSString *script = @"";
    if ([name isEqualToString: HANDLE_CUSTOM_LAUNCHER_VISIBILITY]) {
        script = [NSString stringWithFormat:@"%@.sendEventToJs(`%@`, %s)", serviceName, name,[body boolValue] ? "true" : "false"];
    } else if([body isKindOfClass:[NSNumber class]]){
        script = [NSString stringWithFormat:@"%@.sendEventToJs(`%@`, %@)", serviceName, name, body];
    } else{
        if([body isKindOfClass:[NSString class]]){
            NSString *jsonString = (NSString*)body;
            jsonString = [jsonString stringByReplacingOccurrencesOfString:@"`" withString:@"\""];
            script = [NSString stringWithFormat: @"%@.sendEventToJs(`%@`, `%@`)", serviceName, name, jsonString];
        }else if(body == nil){
            script = [NSString stringWithFormat: @"%@.sendEventToJs(`%@`)", serviceName, name];
        }
    }
    [self stringByEvaluatingJavaScriptFromString:script];
}

- (NSMutableDictionary *)getChatActionArguments: (SIQChatActionArguments*)arguments withID:(NSString*)actionID actionName:(NSString*)actionName
{
    NSMutableDictionary *argumentsDict = [NSMutableDictionary dictionary];
    if([arguments elementID] != nil){
        NSString *elementID = [arguments elementID];
        [argumentsDict setObject:elementID forKey:@"elementID"];
    }
    [argumentsDict setObject:actionName forKey:@"clientActionName"];
    if([arguments identifier] != nil){
        NSString *identifier = [arguments identifier];
        [argumentsDict setObject:identifier forKey:@"name"];
    }
    if([arguments label] != nil){
        NSString *label = [arguments label];
        [argumentsDict setObject:label forKey:@"label"];
    }
    [argumentsDict setObject:actionID forKey:@"uuid"];
    return argumentsDict;
}

- (NSMutableDictionary *)getFAQCategoryObject: (SIQFAQCategory*) category
{
    NSMutableDictionary *categoryDict = [NSMutableDictionary dictionary];
    if([category id] != nil){
        NSString *categoryID = [category id];
        [categoryDict setObject: categoryID  forKey: @"id"];
        if([category name] != nil)
            [categoryDict setObject: [category name]  forKey: @"name"];
        
        [categoryDict setObject: @([category articleCount])  forKey: @"articleCount"];
    }
    return categoryDict;
}

- (NSMutableDictionary *)getErrorObject: (NSError*) error{
    NSMutableDictionary *errorDict = [NSMutableDictionary dictionary];
    NSInteger code = [error code];
    NSString* message = [error localizedDescription];
    [errorDict setObject: @(code)  forKey: @"code"];
    [errorDict setObject: message  forKey: @"message"];
    return errorDict;
}

- (NSMutableDictionary *)getSIQErrorObject:(id<SIQError>)siqError {
    NSMutableDictionary *errorDictionary = [NSMutableDictionary dictionary];
    [errorDictionary setObject:@(siqError.code) forKey:@"code"];
    [errorDictionary setObject:siqError.message forKey:@"message"];
    return errorDictionary;
}

- (NSMutableDictionary *)prepareResourceInformation:(enum SIQResourceType)type resource:(SIQKnowledgeBaseResource * _Nullable)resource {
    NSMutableDictionary *resourceInformation = [NSMutableDictionary dictionary];
    NSMutableDictionary *resourceObject = [self getResourceObject:resource];
    
    if (resourceObject != nil) {
        [resourceInformation setObject:resourceObject forKey:@"resource"];
    }
    
    if (type == SIQResourceTypeArticles) {
        [resourceInformation setObject:RESOURCE_ARTICLES forKey:@"type"];
    }
    
    return resourceInformation;
}

- (NSMutableArray *)getResourceList: (NSArray<SIQKnowledgeBaseResource *> *) resources {
    NSMutableArray *resourceArray = [NSMutableArray array];
    
    NSInteger i = 0;
    for (SIQKnowledgeBaseResource *resource in resources){
        NSMutableDictionary *resourceDict = [NSMutableDictionary dictionary];
        resourceDict = [self getResourceObject:resource];
        [resourceArray insertObject:resourceDict atIndex:i];
        i = i + 1;
    }
    return resourceArray;
}

- (NSMutableArray *)getCategoryList: (NSArray<SIQKnowledgeBaseCategory *> *) categories
{
    NSMutableArray *categoryArray = [NSMutableArray array];
    
    NSInteger i = 0;
    for (SIQKnowledgeBaseCategory *category in categories){
        NSMutableDictionary *categoryDict = [NSMutableDictionary dictionary];
        categoryDict = [self getCategoryObject:category];
        [categoryArray insertObject:categoryDict atIndex:i];
        i = i + 1;
    }
    return categoryArray;
}

- (NSMutableDictionary *)getCategoryObject: (SIQKnowledgeBaseCategory*)category {
    NSMutableDictionary *categoryDictionary = [NSMutableDictionary dictionary];
    if([category id] != nil) {
        NSString *categoryID = [category id];
        [categoryDictionary setObject: categoryID  forKey: @"id"];
        
        if ([category name] != nil) {
            [categoryDictionary setObject: [category name]  forKey: @"name"];
        }
        if ([category departmentId] != nil) {
            [categoryDictionary setObject: [category departmentId]  forKey: @"departmentId"];
        }
        if ([category count] != nil) {
            [categoryDictionary setObject: [category count]  forKey: @"count"];
        }
        if ([category childrenCount] != nil) {
            [categoryDictionary setObject: [category childrenCount]  forKey: @"childrenCount"];
        }
        if ([category order] != nil) {
            [categoryDictionary setObject: [category order]  forKey: @"order"];
        }
        if ([category parentCategoryId] != nil) {
            [categoryDictionary setObject: [category parentCategoryId]  forKey: @"parentCategoryId"];
        }
        if ([category resourceModifiedTime] != nil) {
            NSDate *modifiedTime = [category resourceModifiedTime];
            int time = (int)[modifiedTime timeIntervalSince1970];
            [categoryDictionary setObject: @(time) forKey: @"resourceModifiedTime"];
        }
    }
    return categoryDictionary;
}

- (NSMutableDictionary *)getResourceObject: (SIQKnowledgeBaseResource*)resource {
    NSMutableDictionary *resourceDictionary = [NSMutableDictionary dictionary];
    if([resource id] != nil){
        NSString *articleID = [resource id];
        [resourceDictionary setObject: articleID  forKey: @"id"];
        
        if ([resource category] != nil) {
            NSMutableDictionary *resourceCategory = [NSMutableDictionary dictionary];
            if ([[resource category] id] != nil) {
                [resourceCategory setObject: [[resource category] id]  forKey: @"id"];
            }
            if ([[resource category] name] != nil) {
                [resourceCategory setObject: [[resource category] name]  forKey: @"name"];
            }
            [resourceDictionary setObject: resourceCategory  forKey: @"category"];
        }
        
        if ([resource title] != nil) {
            [resourceDictionary setObject: [resource title]  forKey: @"title"];
        }
        
        if ([resource departmentId] != nil) {
            [resourceDictionary setObject: [resource departmentId]  forKey: @"departmentId"];
        }
        
        if ([resource language] != nil) {
            NSMutableDictionary *resourceLanguage = [NSMutableDictionary dictionary];
            if ([[resource language] id] != nil) {
                [resourceLanguage setObject: [[resource language] id]  forKey: @"id"];
            }
            if ([[resource language] code] != nil) {
                [resourceLanguage setObject: [[resource language] code]  forKey: @"code"];
            }
            [resourceDictionary setObject: resourceLanguage  forKey: @"language"];
        }
        
        if ([resource creator] != nil) {
            NSMutableDictionary *resourceCreator = [NSMutableDictionary dictionary];
            if ([[resource creator] id] != nil) {
                [resourceCreator setObject: [[resource creator] id]  forKey: @"id"];
            }
            if ([[resource creator] name] != nil) {
                [resourceCreator setObject: [[resource creator] name]  forKey: @"name"];
            }
            if ([[resource creator] email] != nil) {
                [resourceCreator setObject: [[resource creator] email]  forKey: @"email"];
            }
            if ([[resource creator] displayName] != nil) {
                [resourceCreator setObject: [[resource creator] displayName]  forKey: @"displayName"];
            }
            if ([[resource creator] imageUrl] != nil) {
                [resourceCreator setObject: [[resource creator] imageUrl]  forKey: @"imageUrl"];
            }
            [resourceDictionary setObject: resourceCreator  forKey: @"creator"];
        }
        
        if ([resource modifier] != nil) {
            NSMutableDictionary *resourceModifier = [NSMutableDictionary dictionary];
            if ([[resource modifier] id] != nil) {
                [resourceModifier setObject: [[resource modifier] id]  forKey: @"id"];
            }
            if ([[resource modifier] name] != nil) {
                [resourceModifier setObject: [[resource modifier] name]  forKey: @"name"];
            }
            if ([[resource modifier] email] != nil) {
                [resourceModifier setObject: [[resource modifier] email]  forKey: @"email"];
            }
            if ([[resource modifier] displayName] != nil) {
                [resourceModifier setObject: [[resource modifier] displayName]  forKey: @"displayName"];
            }
            if ([[resource modifier] imageUrl] != nil) {
                [resourceModifier setObject: [[resource modifier] imageUrl]  forKey: @"imageUrl"];
            }
            [resourceDictionary setObject: resourceModifier  forKey: @"modifier"];
        }
        
        if ([resource createdTime] != nil) {
            NSDate *createdTime = [resource createdTime];
            int time = (int)[createdTime timeIntervalSince1970];
            [resourceDictionary setObject: @(time) forKey: @"createdTime"];
        }
        
        if ([resource modifiedTime] != nil) {
            NSDate *createdTime = [resource modifiedTime];
            int time = (int)[createdTime timeIntervalSince1970];
            [resourceDictionary setObject: @(time) forKey: @"modifiedTime"];
        }
        
        if ([resource publicUrl] != nil) {
            [resourceDictionary setObject: [resource publicUrl]  forKey: @"publicUrl"];
        }
        
        if ([resource stats] != nil) {
            NSMutableDictionary *resourceStats = [NSMutableDictionary dictionary];
            if ([[resource stats] liked] != nil) {
                [resourceStats setObject: [[resource stats] liked]  forKey: @"liked"];
            }
            if ([[resource stats] disliked] != nil) {
                [resourceStats setObject: [[resource stats] disliked]  forKey: @"disliked"];
            }
            if ([[resource stats] used] != nil) {
                [resourceStats setObject: [[resource stats] used]  forKey: @"used"];
            }
            if ([[resource stats] viewed] != nil) {
                [resourceStats setObject: [[resource stats] viewed]  forKey: @"viewed"];
            }
            [resourceDictionary setObject: resourceStats  forKey: @"stats"];
        }
        
        if ([resource content] != nil) {
            [resourceDictionary setObject: [resource content]  forKey: @"content"];
        }
        
        SIQArticleRatedType ratedType = [resource ratedType];
        if (ratedType == SIQArticleRatedTypeLiked) {
            [resourceDictionary setObject: @"liked" forKey:@"ratedType"];
        }
        if (ratedType == SIQArticleRatedTypeDisliked) {
            [resourceDictionary setObject: @"disliked" forKey:@"ratedType"];
        }
        
    }
    return resourceDictionary;
}

- (NSMutableArray *)getFAQCategoryList: (NSArray<SIQFAQCategory *> *) categories
{
    NSMutableArray *categoryArray = [NSMutableArray array];
    
    NSInteger i = 0;
    for (SIQFAQCategory *category in categories){
        
        NSMutableDictionary *categoryDict = [NSMutableDictionary dictionary];
        if([category id] != nil){
            categoryDict = [self getFAQCategoryObject:category];
            [categoryArray insertObject:categoryDict atIndex:i];
            i = i + 1;
        }
    }
    return categoryArray;
}

- (NSMutableDictionary *)getFAQArticleObject: (SIQFAQArticle*) article
{
    NSMutableDictionary *articleDict = [NSMutableDictionary dictionary];
    if([article id] != nil){
        
        NSString *articleID = [article id];
        [articleDict setObject: articleID  forKey: @"id"];
        
        if([article categoryID] != nil)
            [articleDict setObject: [article categoryID]  forKey: @"categoryID"];
        
        if([article categoryName] != nil)
            [articleDict setObject: [article categoryName]  forKey: @"categoryName"];
        
        if([article lastModifiedTime] != nil){
            NSDate *modifiedTime = [article lastModifiedTime];
            int time = (int)[modifiedTime timeIntervalSince1970];
            [articleDict setObject: @(time) forKey: @"modifiedTime"];
        }
        
        if([article createdTime] != nil){
            NSDate *modifiedTime = [article createdTime];
            int time = (int)[modifiedTime timeIntervalSince1970];
            [articleDict setObject: @(time) forKey: @"createdTime"];
        }
        
        if([article name] != nil)
            [articleDict setObject: [article name]  forKey: @"name"];
        
        [articleDict setObject: @([article viewCount])  forKey: @"viewCount"];
        
        [articleDict setObject: @([article likeCount])  forKey: @"likeCount"];
        
        [articleDict setObject: @([article dislikeCount])  forKey: @"dislikeCount"];
    }
    return articleDict;
}

- (NSMutableArray *)getArticlesList: (NSArray<SIQFAQArticle *> *) articles
{
    NSMutableArray *articlesArray = [NSMutableArray array];
    for (SIQFAQArticle *article in articles){
        
        NSMutableDictionary *articleDict = [NSMutableDictionary dictionary];
        
        NSInteger i = 0;
        if([article id] != nil){
            articleDict = [self getFAQArticleObject:article];
            [articlesArray insertObject:articleDict atIndex:i];
            i = i + 1;
        }
    }
    return articlesArray;
}

- (NSMutableDictionary *)getChatObject: (SIQVisitorChat*) chat
{
    NSMutableDictionary *chatDict = [NSMutableDictionary dictionary];
    if([chat referenceID] != nil){
        
        NSString *id = [chat referenceID];
        [chatDict setObject: id  forKey: @"id"];
        
        if([chat attenderEmail] != nil){
            [chatDict setObject: [chat attenderEmail]  forKey: @"attenderEmail"];
        }
        if([chat attenderID] != nil){
            [chatDict setObject: [chat attenderID]  forKey: @"attenderID"];
        }
        if([chat attenderName] != nil){
            [chatDict setObject: [chat attenderName]  forKey: @"attenderName"];
        }
        if([chat departmentName] != nil){
            [chatDict setObject: [chat departmentName]  forKey: @"departmentName"];
        }
        
        [chatDict setObject: [NSNumber numberWithBool: [chat isBotAttender]]   forKey: @"isBotAttender"];
        
        if ([[chat lastMessage] text] != nil) {
            [chatDict setObject: [[chat lastMessage] text]  forKey: @"lastMessage"];
        }
        NSMutableDictionary *recentMessageDict = [NSMutableDictionary dictionary];
        NSMutableDictionary *fileMessageDict = [NSMutableDictionary dictionary];
        if ([chat lastMessage] != nil){
            if ([[chat lastMessage] file] != nil){
                NSString *fileContent = [[[chat lastMessage] file] contentType];
                NSString *comment = [[[chat lastMessage] file] comment];
                NSString *fileName = [[[chat lastMessage] file] name];
                NSInteger fileSize = [[[chat lastMessage] file] size];
                
                if (fileName != nil) {
                    [fileMessageDict setObject: fileName  forKey: @"name"];
                }
                if (fileContent != nil) {
                    [fileMessageDict setObject: fileContent  forKey: @"content_type"];
                }
                if (comment != nil) {
                    [fileMessageDict setObject: comment  forKey: @"comment"];
                }
                if (fileSize != nil) {
                    [fileMessageDict setObject: @(fileSize)   forKey: @"size"];
                }
                
                if (fileContent != nil){
                    if (comment != nil){
                        [chatDict setObject:[NSString stringWithFormat:@"%@:%@",fileContent,comment]  forKey: @"lastMessage"];
                    }else{
                        [chatDict setObject:fileContent  forKey: @"lastMessage"];
                    }
                }
            } else if ([[chat lastMessage] text] != nil){
                NSString *text = [[chat lastMessage] text];
                [chatDict setObject: text  forKey: @"lastMessage"];
                [recentMessageDict setObject: text  forKey: @"text"];
            }
            [recentMessageDict setObject: fileMessageDict  forKey: @"file"];
            NSString *sender = [[chat lastMessage] sender];
            if( sender != nil){
                [chatDict setObject: sender  forKey: @"lastMessageSender"];
                [recentMessageDict setObject: sender  forKey: @"sender"];
            }
            
            NSDate *messageTime = [[chat lastMessage] time];
            if (messageTime != nil){
                int time = (int)[messageTime timeIntervalSince1970];
                [chatDict setObject: @(time) forKey: @"lastMessageTime"];
                [recentMessageDict setObject: @(time)  forKey: @"time"];
            }
            [chatDict setObject: recentMessageDict  forKey: @"recentMessage"];
        }
        if([chat question] != nil){
            [chatDict setObject: [chat question]  forKey: @"question"];
        }
        
        if([chat feedback] != nil){
            [chatDict setObject:[chat feedback] forKey:@"feedback"];
        }
        
        if([chat rating] != nil){
            [chatDict setObject:[chat rating] forKey:@"rating"];
        }
        
        ChatStatus status = [chat status];
        
        if (status == ChatStatusTriggered){
            [chatDict setObject: TYPE_TRIGGERED  forKey: @"status"];
        }else if (status == ChatStatusProactive){
            [chatDict setObject: TYPE_PROACTIVE  forKey: @"status"];
        }else if (status == ChatStatusConnected){
            [chatDict setObject: TYPE_CONNECTED  forKey: @"status"];
        }else if (status == ChatStatusWaiting){
            [chatDict setObject: TYPE_WAITING  forKey: @"status"];
        }else if (status == ChatStatusMissed){
            [chatDict setObject: TYPE_MISSED  forKey: @"status"];
        }else if (status == ChatStatusClosed){
            [chatDict setObject: TYPE_CLOSED  forKey: @"status"];
        }
        
        [chatDict setObject: @([chat unreadCount])  forKey: @"unreadCount"];
        
        NSInteger queuePosition = [chat queuePosition];
        if(queuePosition > 0) {
            [chatDict setObject: @(queuePosition)  forKey: @"queuePosition"];
        }
        
    }
    return chatDict;
}

- (NSMutableDictionary *)getVisitorObject: (SIQVisitor*)arguments {
    
    NSMutableDictionary *visitorDict = [NSMutableDictionary dictionary];
    
    if([arguments name] != nil){
        NSString *name = [arguments name];
        [visitorDict setObject:name forKey:@"name"];
    }
    
    if([arguments browser] != nil){
        NSString *browser = [arguments browser];
        [visitorDict setObject:browser forKey:@"browser"];
    }
    
    if([arguments city] != nil){
        NSString *city = [arguments city];
        [visitorDict setObject:city forKey:@"city"];
    }
    
    if([arguments countryCode] != nil){
        NSString *countryCode = [arguments countryCode];
        [visitorDict setObject:countryCode forKey:@"countryCode"];
    }
    
    if([arguments state] != nil){
        NSString *state = [arguments state];
        [visitorDict setObject:state forKey:@"state"];
    }
    
    if([arguments email] != nil){
        NSString *email = [arguments email];
        [visitorDict setObject:email forKey:@"email"];
    }
    
    if([arguments lastVisitTime] != nil){
        NSDate *lastVisitTime = [arguments lastVisitTime];
        int time = (int)[lastVisitTime timeIntervalSince1970];
        [visitorDict setObject: @(time) forKey: @"lastVisitTime"];
    }
    
    if([arguments ip] != nil){
        NSString *ip = [arguments ip];
        [visitorDict setObject:ip forKey:@"ip"];
    }
    
    if([arguments firstVisitTime] != nil){
        NSDate *firstVisitTime = [arguments firstVisitTime];
        int time = (int)[firstVisitTime timeIntervalSince1970];
        [visitorDict setObject: @(time) forKey: @"firstVisitTime"];
    }
    
    if([arguments name] != nil){
        NSString *name = [arguments name];
        [visitorDict setObject:name forKey:@"name"];
    }
    
    if([arguments noOfDaysVisited] != nil){
        NSNumber *noOfDaysVisited = [arguments noOfDaysVisited];
        [visitorDict setObject:noOfDaysVisited forKey:@"noOfDaysVisited"];
    }
    
    if([arguments numberOfChats] != nil){
        NSNumber *numberOfChats = [arguments numberOfChats];
        [visitorDict setObject:numberOfChats forKey:@"numberOfChats"];
    }
    
    if([arguments numberOfVisits] != nil){
        NSNumber *numberOfVisits = [arguments numberOfVisits];
        [visitorDict setObject:numberOfVisits forKey:@"numberOfVisits"];
    }
    
    if([arguments totalTimeSpent] != nil){
        NSNumber *totalTimeSpent = [arguments totalTimeSpent];
        [visitorDict setObject:totalTimeSpent forKey:@"totalTimeSpent"];
    }
    
    if([arguments os] != nil){
        NSString *os = [arguments os];
        [visitorDict setObject:os forKey:@"os"];
    }else{
        [visitorDict setObject:@"iOS" forKey:@"os"];
    }
    
    if([arguments phone] != nil){
        NSString *phone = [arguments phone];
        [visitorDict setObject:phone forKey:@"phone"];
    }
    
    if([arguments region] != nil){
        NSString *region = [arguments region];
        [visitorDict setObject:region forKey:@"region"];
    }
    
    if([arguments searchEngine] != nil){
        NSString *searchEngine = [arguments searchEngine];
        [visitorDict setObject:searchEngine forKey:@"searchEngine"];
    }
    
    if([arguments searchQuery] != nil){
        NSString *searchQuery = [arguments searchQuery];
        [visitorDict setObject:searchQuery forKey:@"searchQuery"];
    }
    
    return visitorDict;
}

- (NSMutableDictionary *)getDepartmentObject: (SIQDepartment*)argument {
    
    NSMutableDictionary *departmentDictionary = [NSMutableDictionary dictionary];
    
    NSString *departmentName = [argument name];
    NSString *departmentID = [argument id];
    BOOL departmentAvailable = [argument available];
    
    if(departmentID != nil){
        [departmentDictionary setObject:departmentID forKey:@"id"];
    }
    
    if(departmentName != nil){
        [departmentDictionary setObject:departmentName forKey:@"name"];
    }
    
    [departmentDictionary setObject: [NSNumber numberWithBool: departmentAvailable]   forKey: @"available"];
    
    return departmentDictionary;
    
}


- (NSMutableArray *)getChatList: (NSArray<SIQVisitorChat *> *) chats
{
    NSMutableArray *chatsArray = [NSMutableArray array];
    
    NSInteger i = 0;
    for (SIQVisitorChat *chat in chats) {
        NSMutableDictionary *chatDict = [NSMutableDictionary dictionary];
        chatDict = [self getChatObject:chat];
        [chatsArray insertObject:chatDict atIndex:i];
        i = i + 1;
    }
    return chatsArray;
}

- (NSMutableArray *)getDepartmentList: (NSArray<SIQDepartment *> *) departments
{
    NSMutableArray *departmentsArray = [NSMutableArray array];
    NSInteger i = 0;
    for (SIQDepartment *department in departments) {
        NSMutableDictionary *departmentDictionary = [NSMutableDictionary dictionary];
        departmentDictionary = [self getDepartmentObject:department];
        [departmentsArray insertObject:departmentDictionary atIndex:i];
        i = i + 1;
    }
    return departmentsArray;
}



- (void)agentsOffline {
    [self sendEvent:OPERATORS_OFFLINE body:nil];
}

- (void)agentsOnline {
    [self sendEvent:OPERATORS_ONLINE body:nil];
}

- (void)chatViewClosedWithId:(NSString * _Nullable)id {
    [self sendEvent:CHATVIEW_CLOSED body:id];
}

- (void)chatViewOpenedWithId:(NSString * _Nullable)id {
    [self sendEvent:CHATVIEW_OPENED body:id];
}

- (void)homeViewClosed {
    [self sendEvent:HOMEVIEW_CLOSED body:nil];
}

- (void)homeViewOpened {
    [self sendEvent:HOMEVIEW_OPENED body:nil];
}

- (void)supportClosed {
    [self sendEvent:SUPPORT_CLOSED body:nil];
}

- (void)supportOpened {
    [self sendEvent:SUPPORT_OPENED body:nil];
}

- (void)visitorIPBlocked {
    [self sendEvent:VISITOR_IPBLOCKED body:nil];
}

- (void)chatAttendedWithChat:(SIQVisitorChat * _Nullable)chat {
    [self sendEvent:CHAT_ATTENDED body:[self getChatObject:chat]];
}

- (void)chatClosedWithChat:(SIQVisitorChat * _Nullable)chat {
    [self sendEvent:CHAT_CLOSED body:[self getChatObject:chat]];
}

- (void)chatFeedbackRecievedWithChat:(SIQVisitorChat * _Nullable)chat {
    [self sendEvent:FEEDBACK_RECEIVED body:[self getChatObject:chat]];
}

- (void)chatMissedWithChat:(SIQVisitorChat * _Nullable)chat {
    [self sendEvent:CHAT_MISSED body:[self getChatObject:chat]];
}

- (void)chatOpenedWithChat:(SIQVisitorChat * _Nullable)chat {
    [self sendEvent:CHAT_OPENED body:[self getChatObject:chat]];
}

- (void)chatQueuePositionChangedWithChat:(SIQVisitorChat *)chat {
    [self sendEvent:CHAT_QUEUE_POSITION_CHANGED body:[self getChatObject:chat]];
}

- (void)chatRatingRecievedWithChat:(SIQVisitorChat * _Nullable)chat {
    [self sendEvent:RATING_RECEIVED body:[self getChatObject:chat]];
}

- (void)chatReopenedWithChat:(SIQVisitorChat * _Nullable)chat {
    [self sendEvent:CHAT_REOPENED body:[self getChatObject:chat]];
}

- (void)unreadCountChanged:(NSInteger)count {
    NSNumber *numCount = @(count);
    [self sendEvent:UNREAD_COUNT_CHANGED body:numCount];
}

- (void)articleClosedWithId:(NSString * _Nullable)id {
    [self sendEvent:ARTICLE_CLOSED body:id];
}

- (void)articleDislikedWithId:(NSString * _Nullable)id {
    [self sendEvent:ARTICLE_DISLIKED body:id];
}

- (void)articleLikedWithId:(NSString * _Nullable)id {
    [self sendEvent:ARTICLE_LIKED body:id];
}

- (void)articleOpenedWithId:(NSString * _Nullable)id {
    [self sendEvent:ARTICLE_OPENED body:id];
}

- (void)handleTriggerWithName:(NSString *)name visitorInformation:(SIQVisitor *)visitorInformation{
    
    NSMutableDictionary *triggerInformation = [NSMutableDictionary dictionary];
    
    NSMutableDictionary *visitorInfo = [self getVisitorObject:visitorInformation];
    
    if(visitorInfo != nil){
        [triggerInformation setObject:visitorInfo forKey:@"visitorInformation"];
    }
    
    if(name != nil){
        [triggerInformation setObject:name forKey:@"triggerName"];
    }
    
    [self sendEvent:CUSTOMTRIGGER body:triggerInformation];
    
}

- (void)handleBotTrigger {
    [self sendEvent:BOT_TRIGGER body:nil];
}

- (void)handleResourceOpened:(enum SIQResourceType)type resource:(SIQKnowledgeBaseResource * _Nullable)resource {
    NSMutableDictionary *resourceInformation = [self prepareResourceInformation:type resource:resource];
    [self sendEvent:RESOURCE_OPENED body: resourceInformation];
}

- (void)handleResourceClosed:(enum SIQResourceType)type resource:(SIQKnowledgeBaseResource * _Nullable)resource {
    NSMutableDictionary *resourceInformation = [self prepareResourceInformation:type resource:resource];
    [self sendEvent:RESOURCE_CLOSED body: resourceInformation];
}

- (void)handleResourceLiked:(enum SIQResourceType)type resource:(SIQKnowledgeBaseResource * _Nullable)resource {
    NSMutableDictionary *resourceInformation = [self prepareResourceInformation:type resource:resource];
    [self sendEvent:RESOURCE_LIKED body: resourceInformation];
}

- (void)handleResourceDisliked:(enum SIQResourceType)type resource:(SIQKnowledgeBaseResource * _Nullable)resource {
    NSMutableDictionary *resourceInformation = [self prepareResourceInformation:type resource:resource];
    [self sendEvent:RESOURCE_DISLIKED body: resourceInformation];
}

- (void)handleCustomLauncherVisibility:(BOOL)visible {
    NSNumber *visibleValue = [NSNumber numberWithBool:visible];
    [self sendEvent:HANDLE_CUSTOM_LAUNCHER_VISIBILITY body: visibleValue];
}

- (void)handleNotificationActionWithPayload:(NSDictionary<NSString *,id> *)payload {
    [[ZohoSalesIQ Notification] getPayload: payload  completion:^(SalesIQNotificationPayload * _Nullable completionObject) {
        NSMutableDictionary *resultMap = [NSMutableDictionary dictionary];
        if (completionObject != nil) {
            if ([completionObject isKindOfClass:[SalesIQChatNotificationPayload class]]) {
                SalesIQChatNotificationPayload *chatPayload = (SalesIQChatNotificationPayload *)completionObject;
                resultMap[@"type"] = @"chat";
                resultMap[@"payload"] = [chatPayload toDictionary];
            } else if ([completionObject isKindOfClass:[SalesIQEndChatNotificationPayload class]]) {
                SalesIQEndChatNotificationPayload *endChatPayload = (SalesIQEndChatNotificationPayload *)completionObject;
                resultMap[@"type"] = @"endChatDetails";
                resultMap[@"payload"] = [endChatPayload toDictionary];
            } else if ([completionObject isKindOfClass:[SalesIQVisitorHistoryNotificationPayload class]]) {
                SalesIQVisitorHistoryNotificationPayload *visitorHistoryPayload = (SalesIQVisitorHistoryNotificationPayload *)completionObject;
                resultMap[@"type"] = @"visitorHistory";
                resultMap[@"payload"] = [visitorHistoryPayload toDictionary];
            }
        } else {
            NSLog(@"Completion object is nil");
        }
        [self sendEvent:NOTIFICATION_CLICKED body: resultMap];
    }];
}

@end
