/*****************************************************************************************
Package Name:   com.kt.bit.csm.blds.common
Author:         Pushpendra Pandey
Description:    
This package contains all common classes used across CSM project like constants,codes etc.

Modification Log:   
When                           Version              Who                What 
27-12-2010                     1.0                 Pushpendra Pandey   New class created
04-04-2011                     3.0                 Jasmine Kua         Add new constant JNDI_DATA_SOURCE_NAME 
------------------------------------------------------------------------------------------- 
*********************************************************************************************/
package com.kt.bit.csm.blds.common;


/***************************************************************************
Constants 
=========
Constants interface defines all of the constant values being used in CSM 
application.
*******************************************************************************/

public interface Constants {
    /**************************************************************************
    EncryptionType
    ==============
    **************************************************************************/
    interface EncryptionType {
        public static final String  AES     = "AES";
        public static final String  MD5     = "MD5";
        public static final String  SHA1    = "SHA1";
        public static final String  SHA256  = "SHA256";
    }

    /**************************************************************************
    ProfileTypeID
    =============
    **************************************************************************/
    interface ProfileTypeID {       
        public static final int CHILD_SERVICE_PROFILE   = 5;
        public static final int CREDENTIAL_PROFILE      = 3;
        public static final int ORGANIZATION_PROFILE    = 2;
        public static final int ORGCREDENTIAL_PROFILE   = 4;
        public static final int RESOURCE_PROFILE        = 6;
        public static final int USER_PROFILE            = 1;
    }

    /**************************************************************************
    ServiceDomainCode
    =================
    **************************************************************************/
    interface ServiceDomainCode {
        public static final String  KTSPORTS           = "3";
        public static final String  OLLEH              = "2";
        public static final String  OLLEH_MEMBERSHIP   = "1";
        public static final String  QOOK               = "55";
        public static final String  SHOW               = "104";
    }
    
    /**************************************************************************
    SvcDomainID
    =================
    **************************************************************************/
    interface SvcDomainID {
        public static final String  SVC_DOMAIN_ALL                   = "ALL";
        public static final String  SVC_DOMAIN_OLLEH_MEMBERSHIP      = "101";
        public static final String  SVC_DOMAIN_OLLEH                 = "102";
        public static final String  SVC_DOMAIN_KTSPORTS              = "103";
        public static final String  SVC_DOMAIN_KTMEDIAHUB            = "106";
        public static final String  SVC_DOMAIN_OLLEH_CUSTOMER_CENTER = "107";
    }

    /**************************************************************************
    ServiceDomainName
    =================
    **************************************************************************/
    interface ServiceDomainName {
        public static final String  KTSPORTSAGREEMENT   = "kt sports service agreement";
        public static final String  OLLEHAGREEMENT      = "olleh service agreement";
        public static final String  OLLEHMEMBERSHIP     = "olleh membership";
        public static final String  SHOWAGREEMENT       = "show service agreement";
        public static final String  QOOKAGREEMENT       = "qook.co.kr";
        public static final String  ZONEQOOKAGREEMENT   = "zone.qook.co.kr";
        public static final String  KTMEDIAHUB          = "KT MediaHub";
        public static final String  OLLEHCUSTOMERCENTER = "olleh customer center";
    }

    /**************************************************************************
    IncomingDbActionType
    ====================
    **************************************************************************/
    interface IncomingDbActionType {
        public static final String  INSERT              = "I";
        public static final String  UPDATE              = "U";
        public static final String  RESET               = "R";
        public static final String  DELETE              = "D";
    }
    
    /**************************************************************************
    OnlineActionType
    ====================
    **************************************************************************/
    interface OnlineActionType {
        public static final String  RETRIEVE            = "0";
        public static final String  UPDATE              = "1";
    }

    /**************************************************************************
    ServiceDomainID
    ===============
    **************************************************************************/
    interface ServiceDomainID {
        public static final int QOOK_SERVICE_AGREEMENT_SERVICE_DOMAIN_ID            = 55;
        public static final int QOOK_INTERNET_SERVICE_AGREEMENT_SERVICE_DOMAIN_ID   = 75;
        public static final int OLLEH_MEMBERSHIP_SERVICE_DOMAIN_ID                  = 101;
        public static final int OLLEH_SERVICE_AGREEMENT_SERVICE_DOMAIN_ID           = 102;
        public static final int KTSPORTS_SERVICE_AGREEMENT_SERVICE_DOMAIN_ID        = 103;
        public static final int SHOW_SERVICE_AGREEMENT_SERVICE_DOMAIN_ID            = 104;
        public static final int KT_MEDIAHUB_SERVICE_DOMAIN_ID                       = 106;
        public static final int OLLEH_CUSTOMER_CENTER_DOMAIN_ID                     = 107;
    }

    /**************************************************************************
    OnlineSRH
    =========
    **************************************************************************/
    interface OnlineSRH {
        public static final String  CHECKOLLEHMEMBERSHIP                        = "CheckOllehMembership";
        public static final String  RETRIEVECREDENTIALS                         = "RetrieveCredentials";
        public static final String  CONVERTEXISTINGIDS                          = "ConvertExistingIDs";
        public static final String  MEMBERSHIPSUBSCRIPTION                      = "MembershipSubscription";
        public static final String  CREDENTIALIDAUTHENTICATION                  = "CredentialIDAuthentication";
        public static final String  IDANDPASSWORDAUTHENTICATION                 = "IdAndPasswordAuthentication";
        public static final String  MEMBERSHIPCANCELLATION                      = "MembershipCancellation";
        public static final String  CANCELWEBCREDENTIAL                         = "CancelWebCredential";
        public static final String  CANCELFAMILYMEMBER                          = "CancelFamilyMember";
        public static final String  RETRIEVEPARTYPROFILE                        = "RetrievePartyProfile";
        public static final String  UPDATEUSERPROFILE                           = "UpdateUserProfile";
        public static final String  RETRIEVEPARTYREALNAMECHECK                  = "RetrievePartyRealNameCheck";
        public static final String  RETRIEVESERVICESUBSCRIPTION                 = "RetrieveServiceSubscription";
        public static final String  UNREGISTERSERVICESUBSCRIPTION               = "UnRegisterServiceSubscription";
        public static final String  RETRIEVEPARTYCONTACT                        = "RetrievePartyContact";
        public static final String  RETRIEVEOLLEHCREDENTIALBYUSERNAME           = "RetrieveOllehCredentialByUsername";
        public static final String  UPGRADEMEMBERSHIP                           = "UpgradeMembership";
        public static final String  DNAUTHENTICATION                            = "DNAuthentication";
        public static final String  UPDATEPASSWORDEXPIRY                        = "UpdatePasswordExpiry";
        public static final String  UPDATEPASSWORD                              = "UpdatePassword";
        public static final String  AUTHENTICATEPASSWORD                        = "AuthenticatePassword";
        public static final String  RETRIEVESECRETQUESTION                      = "RetrieveSecretQuestion";
        public static final String  IPINCONVERSION                              = "IPINConversion";
        public static final String  VALIDATESECRETANSWER                        = "ValidateSecretAnswer";
        public static final String  REGISTERCARD                                = "RegisterCard";
        public static final String  SERVICESUBSCRIPTION                         = "ServiceSubscription";
        public static final String  REGISTERDN                                  = "RegisterDN";
        public static final String  RETRIEVEFAMILYMEMBER                        = "RetrieveFamilyMember";
        public static final String  RETRIEVEPRODUCTLIST                         = "RetrieveProductList";
        public static final String  RETRIEVESERVICEDOMAINS                      = "RetrieveServiceDomains";
        public static final String  MANAGESERVICEDOMAIN                         = "ManageServiceDomain";
        public static final String  RETRIEVEMOBILEUSERINFO                      = "RetrieveMobileUserInfo";
        public static final String  ADDSLAVEID                                  = "AddSlaveID";
        public static final String  REGISTERQOOKINTERNET                        = "RegisterQookInternet";
        public static final String  UNREGISTERPRODUCT                           = "UnregisterProduct";
        public static final String  MANAGESLAVEIDSTATUS                         = "ManageSlaveIDStatus";
        public static final String  MANAGEFAMILYMEMBERVAS                       = "ManageFamilyMemberVAS";
        public static final String  MANAGEOPENEMAILID                           = "ManageOpenEmailID";
        public static final String  RETRIEVECREDENTIALVAS                       = "RetrieveCredentialVAS";
        public static final String  CHECKUSERSERVICESUBSCRIPTION                = "CheckUserServiceSubscription";
        public static final String  UPDATEFAMILYTOMASTER                        = "UpdateFamilyToMaster";
        public static final String  RETRIEVEPARTYREGISTEREDTOMOBILE             = "RetrievePartyRegisteredToMobile";
        public static final String  REGISTERPRODUCT                             = "RegisterProduct";
        public static final String  REGISTERRESOURCE                            = "RegisterResource";
        public static final String  RETRIEVERESOURCELISTBYCREDENTIALID          = "RetrieveResourceListByCredentialID";
        public static final String  RETRIEVECREDENTIALSBYUSERNAME               = "RetrieveCredentialsByUsername";
        public static final String  CHANGEUSERNAME                              = "ChangeUsername";
        public static final String  RETRIEVESUBSCRIPTIONRESOURCE                = "RetrieveSubscriptionResource";
        public static final String  MANAGEREPRESENTATIVENUMBER                  = "ManageRepresentativeNumber";
        public static final String  RETRIEVEQOOKANDSHOWINFO                     = "RetrieveQookAndShowInfo";
        public static final String  RETRIEVESUBSCRIPTIONBYUSERNAME              = "RetrieveSubscriptionByUserName";
        public static final String  CHECKPRODUCTVALIDATION                      = "CheckProductValidation";
        public static final String  RETRIEVECASESENSITIVEUSERNAMES              = "RetrieveCaseSensitiveUsernames";
        public static final String  MANAGECANCELPARTY                           = "ManageCancelParty";
        public static final String  RETRIEVEQOOKINTERNETSUBSCPNID               = "RetrieveQookInternetSubscpnID";
        public static final String  IDANDUSERNAMEVERIFICATION                   = "IdAndUsernameVerification";
        public static final String  CHECKFAMILYMEMBERSHIP                       = "CheckFamilyMembership";
        public static final String  CHECKOLLEHIDEXISTFORQOOKINTERNET            = "CheckOllehIDExistForQookInternet";
        public static final String  ADDPARENTALAGREEMENTINFORMATION             = "AddParentalAgreementInformation";
        public static final String  RETRIEVEPARTYNAMESREGISTEREDTOPHONENUMBER   = "RetrievePartyNamesRegisteredToPhoneNumber";
        public static final String  GETMAPPEDQOOKIDSHOWID                       = "GetMappedQookIDShowID";
        public static final String  GETCORPORATIONINFO                          = "GetCorporationInfo";
        public static final String  GETSUBSCRIPTIONSTATUS                       = "GetSubscriptionStatus";
        public static final String  CHECKOWNERSSN                               = "CheckOwnerSSN";
        public static final String  VERIFYWIRELESSPINCODE                       = "VerifyWirelessPinCode";
        public static final String  SETANDINQUIREREPRESENTATIVENUMBER           = "SetAndInquireRepresentativeNumber";
        public static final String  RETRIEVESUBSCRIPTIONRELATIONINFO            = "RetrieveSubscriptionRelationInfo";
        public static final String  GETOLDQOOKANDSHOWID                         = "GetOldQookAndShowID";
        public static final String  GETCONTACTSUBSCRIPTIONINFO                  = "GetContactSubscriptionInfo";
        public static final String  UPDATEREALNAMECHECK                         = "UpdateRealNameCheck";
        public static final String  GETWEBPHONEDETAILINFO                       = "GetWebPhoneDetailInfo";
        public static final String  GETCREDENTIALPASSWORD                       = "GetCredentialPassword";
        public static final String  GETSUBSCRIPTIONINFOBYSAID                   = "GetSubscriptionInfoBySaId";
        public static final String  GETOWNSUBSCRIPTIONSBYPARTYID                = "GetOwnSubscriptionsByPartyID";
        public static final String  REALNAMECHECKUNDER14                        = "RealNameCheckUnder14";
        public static final String  MANAGEWIRELESSPINCODE                       = "ManageWirelessPinCode";
        public static final String  RETRIEVECANCELPARTYLISTBYSEARCH             = "RetrieveCancelPartyListBySearch";
        public static final String  RETRIEVECREDENTIALLISTBYSEARCH              = "RetrieveCredentialListBySearch";
        public static final String  RETRIEVECREDENTIALPARTYINFORMATION          = "RetrieveCredentialPartyInformation";
        public static final String  RETRIEVERESOURCELISTINFORMATION             = "RetrieveResourceListInformation";
        public static final String  RETRIEVESOIPLISTBYSEARCH                    = "RetrieveSoIPListBySearch";
        public static final String  RETRIEVESUBSCRIPTIONINFORMATION             = "RetrieveSubscriptionInformation";
        public static final String  RETRIEVETORELATEINFOOFCREDENTIAL            = "RetrieveToRelateInfoOfCredential";
        public static final String  UPDATECREDENTIALSTATUS                      = "UpdateCredentialStatus";
        public static final String  RETRIEVECSMUIPRODUCTLIST                    = "RetrieveCsmUiProductList";
        public static final String  RETRIEVECREDTINFOSUBSCPNSVCBYCREDENTIALID   = "RetrieveCredtInfoSubscpnSvcByCredentialID";
        public static final String  GETMOBILEUSERANDSUBSCRIPTIONINFO            = "GetMobileUserAndSubscriptionInfo";
        public static final String  UPDATESUBSCRIPTIONSTATUS                    = "UpdateSubscriptionStatus";
        public static final String  GETSSVREGISTRATIONBYARS                     = "GetSSVRegistrationByARS";
        public static final String  GETSSVREGISTRATIONBYCOUNSEL                 = "GetSSVRegistrationByCounsel";
        public static final String  GETSSVREGISTRATIONBYWEB                     = "GetSSVRegistrationByWeb";
        public static final String  RELEASERESERVEDID                           = "ReleaseReservedID";
        public static final String  COHERENCECLEAR                              = "CoherenceClear";
        public static final String  RETRIEVETHIRDPARTYMOBILE                    = "RetrieveThirdPartyMobile";
        public static final String  MANAGETHIRDPARTYMOBILE                      = "ManageThirdPartyMobile";
        public static final String  RETRIEVESOURCESYSPARTYMAPBYCREDENTIAL       = "RetrieveSourceSysPartyMapByCredential";
        public static final String  RETRIEVEMOBILENUMBERVALIDATION              = "RetrieveMobileNumberValidation";
        public static final String  RETRIEVESUBSCRIPTIONANDACCOUNT              = "RetrieveSubscriptionAndAccount";
        public static final String  MANAGEUSERSERVICEAGREEMENTSTATUS            = "ManageUserServiceAgreementStatus";
        public static final String  UPDATEUSERCONTACT                           = "UpdateUserContact";
        public static final String  MANAGEEMAILINFORMATION                      = "ManageEmailInformation";
        public final static String  CHECKPRODUCTVALIDATIONWOSSN                 = "CheckProductValidationWoSsn";
        public final static String  CHECKUSERSERVICESUBSCRIPTIONWOSSN           = "CheckUserServiceSubscriptionWoSsn";
        public final static String  IDANDPASSWORDAUTHENTICATIONWOSSN            = "IdAndPasswordAuthenticationWoSsn";
        public final static String  REGISTERPRODUCTWOSSN                        = "RegisterProductWoSsn";
        public final static String  RETRIEVECREDENTIALSWOSSN                    = "RetrieveCredentialsWoSsn";
        public final static String  RETRIEVEPRODUCTLISTWOSSN                    = "RetrieveProductListWoSsn";
        public final static String  GETMOBILEUSERSUBSCRIPTIONSTATUS             = "GetMobileUserSubscriptionStatus";
        public final static String  GETRELATEDSUBSCRIPTIONINFO                  = "GetRelatedSubscriptionInfo";
        public final static String  GETSPECIFICSUBSCRIPTIONINFO                 = "GetSpecificSubscriptionInfo";
        public final static String  GETOWNEROWNEDSUBSCRIPTIONINFO               = "GetOwnerOwnedSubscriptionInfo";
        public final static String  GETSOURCEIDBYSOURCEID                       = "GetSourceIdBySourceId";
        public final static String  GETACCOUNTLISTINFO                          = "GetAccountListInfo";
        public final static String  REPROCESSCSMDATAFORCAPRI                    = "ReprocessCSMDataforCAPRI";
        public final static String  DCBAPPROVESUBSCRIBER                        = "DCBApproveSubscriber";
        public final static String  DCBAUTH                                     = "DCBAuth";
        public final static String  DCBGETPROVISIONING                          = "DCBGetProvisioning";
        public final static String  DCBGETSUBSCRIBERADDRESS                     = "DCBGetSubscriberAddress";
        public final static String  GETAUTHENTICATESSVANDSUBSCRIPTIONINFO       = "GetAuthenticateSSVAndSubscriptionInfo";
        public final static String  GETBASICMOBILEANDUSERINFO                   = "GetBasicMobileAndUserInfo";
        public final static String  GETSPECIFICSUBSCPNINFOBYSOIP                = "GetSpecificSubscpnInfoBySoip";
        public final static String  GETOWNERALLSTATUSSUBSCPNTYPEINFO            = "GetOwnerAllStatusSubscpnTypeInfo";
        public final static String  CHECKWEBIDBYACCOUNTID                       = "CheckWebIdByAccountId";
        public final static String  GETCOUNTSUBSCPNTYPECD                       = "GetCountSubscpnTypeCd";
        public final static String  GETPARTYANDSUBINFOBYSUBTYPECD               = "GetPartyAndSubInfoBySubTypeCD";
        final String  GETOTMSUBSCRIPTIONINFO                                    = "GetOTMSubscriptionInfo";
        final String  GETOTMSUBSCRIPTIONINFOANDORGPHONE                         = "GetOTMSubscriptionInfoAndOrgPhone";
        final String  GETCUSTOMERINFOANDCTNLIST                                 = "GetCustomerInfoAndCTNList";
        final String  GETPARTYANDPHONERESOURCECOINCIDENCE                       = "GetPartyAndPhoneResourceCoincidence";        
    }

    /**************************************************************************+
    ResourceDetailDefaultTypeCode
    =============================
    **************************************************************************/
    interface ResourceDetailDefaultTypeCode {
        public static final String  CODE_2  = "2";
        public static final String  CODE_3  = "3";
    }

    /**************************************************************************
    CredentialDetailTypeCode
    ========================
    **************************************************************************/
    interface CredentialDetailTypeCode {
        public static final String  OLLEH_WEB_ID                        = "00";
        public static final String  QOOK_WEB_ID                         = "01";
        public static final String  QOOK_ACTIVATION_ID                  = "02";
        public static final String  QOOK_WEB_AND_QOOK_ACTIVATION_ID     = "03";
        public static final String  OLLEH_WEB_AND_QOOK_ACTIVATION_ID    = "04";
    }

    /**************************************************************************
    MembershipStatusCode
    ====================
    **************************************************************************/
    interface MembershipStatusCode {
        public static final String  ACTIVE      = "0";
        public static final String  CANCELLED   = "1";
        public static final String  WAIT        = "2";
    }

    /**************************************************************************
    IncomingPSTNSubscpnServiceVariantStatusCode
    ===========================================
    **************************************************************************/
    interface IncomingPSTNSubscpnServiceVariantStatusCode {
        public static final String  IN_USE      = "A";
        public static final String  DISCARD     = "F";
        public static final String  CANCEL      = "D";
        public static final String  INSTALLING  = "P";
    }

    /**************************************************************************
    IncomingPSTNSubscpnStatusCode
    =============================
    **************************************************************************/
    interface IncomingPSTNSubscpnStatusCode {
        public static final String  IN_NEW_CNSTR        = "00"; 
        public static final String  IN_TEST             = "01"; 
        public static final String  IN_USE              = "02"; 
        public static final String  USG_STOP_TRANS      = "03"; 
        public static final String  USG_STOP            = "04"; 
        public static final String  PAUSE               = "05"; 
        public static final String  PAUSE_NOEXIST       = "06"; 
        public static final String  USG_USEST           = "07"; 
        public static final String  USG_LIMIT           = "08"; 
        public static final String  USG_USEST_NOEXIST   = "09"; 
        public static final String  RESERVE             = "10"; 
        public static final String  RELES_BFR_OPEN_BILL = "11"; 
        public static final String  TERMN_BFR_OPEN      = "12"; 
        public static final String  RELES_BFR_OPEN      = "13"; 
        public static final String  IN_TRSFR_CNSTR      = "14"; 
        public static final String  OAREA_DATA          = "99"; 
    }

    /**************************************************************************
    IncomingPSTNResourceStatusCode
    ==============================
    **************************************************************************/
    interface IncomingPSTNResourceStatusCode {
        public static final String  IN_USE      = "A";
        public static final String  DISCARD     = "F";
        public static final String  CANCEL      = "D";
        public static final String  INSTALLING  = "P";
    }

    /**************************************************************************
    IncomingSubSvcVrntSOCTypeCode
    =============================
    **************************************************************************/
    interface IncomingSubSvcVrntSOCTypeCode {
        public static final String  PRICE_PLAN          = "P";
        public static final String  REDUCED_PRICE_PLAN  = "M";
        public static final String  REGULAR_SOC         = "R";
        public static final String  REDUCED_SOC         = "S";
        public static final String  OPTIONAL_SOC        = "O";
        public static final String  BUY_ONE_GET_ONE_SOC = "B";
    }

    /**************************************************************************
    IncomingSvcVrntSOCTypeCode
    ==========================
    **************************************************************************/
    interface IncomingSvcVrntSOCTypeCode {
        public static final String  PRICE_PLAN                 = "01";
        public static final String  REDUCED_PRICE_PLAN         = "02";
        public static final String  OPTIONAL_SOC               = "03";
        public static final String  REGULAR_SOC                = "04";
        public static final String  REDUCED_SOC                = "05";
        public static final String  BUY_ONE_GET_ONE_SOC        = "06";
    }

    /**************************************************************************
    IncomingBMStatusCode
    ====================
    **************************************************************************/
    interface IncomingBMStatusCode {
        public static final String  BM_STATUS_USE       = "1";
    }

    /**************************************************************************
    ResourceCatalogueDefaultCode
    ============================
    **************************************************************************/
    interface ResourceCatalogueDefaultCode {
        public static final String  S_LCD_TYPE_CD       = "LCD_Type_CD";     // 73
        public static final long    L_LCD_TYPE_CD       = 113;
        public static final String  S_BROWSER_TYPE_CD   = "Browser_Type_CD"; // 74
        public static final long    L_BROWSER_TYPE_CD   = 112;
    }

    /**************************************************************************
    IncomingShowYnSubscpnTypeCode
    =============================
    **************************************************************************/
    interface IncomingShowYnSubscpnTypeCode {
        public static final String  DBDM                = "DBDM";
        public static final String  FMC                 = "FMC";
        public static final String QOOK_INTERNET = "09";
    }

    /**************************************************************************
    TableName
    =========
    **************************************************************************/
    interface TableName {
        public static final String  LNK_SUBSCPN_RESOURCE    = "LNK_SUBSCRIPTION_RESOURCE";
        public static final String  LNK_CREDENTIAL          = "LNK_CREDENTIAL";
    }
    
    /**************************************************************************
    Exceptive SSV(Subscription Service Variant)
    ===========================================
    These are the SSVs sent as main SSVs, but CSM needs to handle as Add-on SSVs.
    **************************************************************************/
    interface ExceptiveSSV {
        public static final String  SSV_2V05                = "2V05";    //KT VOIP Fax Receiving Add-on
        public static final String  SSV_2008                = "2008";    //KT VOIP Number Change Notice Add on
    }
    
    /**************************************************************************
    MobileNumberTypeCode
    ====================
    **************************************************************************/
    interface MobileNumberTypeCode {
        public static final String PHONE_NUMBER             = "01";
        public static final String TABLET_NUMBER            = "02";
    }
    
    /**************************************************************************
    RetrieveSourcePartyMap 
    ====================
    **************************************************************************/
    interface RetrieveSourcePartyMap {
        public static final String TYPE_PERSONAL            = "1";
        public static final String TYPE_ENTERPRISER         = "2";
        public static final int    MAX_ROW                  = 30;
    }
    
    /**************************************************************************
    CodeGroupId 
    ====================
    **************************************************************************/
    interface CodeGroupId {
        public static final int SOURCE_SYSTEM_CD                    = 1;
        public static final int PARTY_TYPE_ID                       = 5;
        public static final int PARTY_DETAIL_TYPE_CD                = 6;
        public static final int PARTY_IDTF_NUMBER_CD                = 7;
        public static final int GENDER_CD                           = 10;
        public static final int BILLING_ACCOUNT_CD                  = 16;
        public static final int PREFER_CONTACT_MEDIA_CD             = 20;
        public static final int CONTACT_TYPE_CD                     = 26;
        public static final int CREDT_TYPE_CD                       = 27;
        public static final int PARTY_SUBSCPN_STATUS_CD             = 29;
        public static final int SUBSCPN_TYPE_CD                     = 36;
        public static final int SUBSCPN_STATUS_CD                   = 37;
        public static final int OLLEH_CREDT_TYPE_CD                 = 73;
    }
    
    /**************************************************************************
    CodeDigits 
    ====================
    **************************************************************************/
    interface CodeDigits {
        public static final int ZERO                                = 0;
        public static final int ONE                                 = 1;
        public static final int TWO                                 = 2;
        public static final int THREE                               = 3;
        public static final int FOUR                                = 4;
        public static final int FIVE                                = 5;
    }
    
    public static final String  DEFAULT_RESOURCE_AUTHENTICATION_ID          = "3";
    public static final int     DEFAULT_STATUS_ID                           = 0;
    
    public static final int     DEFAULT_MAX_ROW_COUNT                       = 30;
    public static final int     DEFAULT_MAX_ROW_COUNT2                      = 100;
    public static final int     TIMECOSERVICE_QOOKINTERNET_MAX_CNT          = 2000;
    public static final int     MAX_CTN_COUNT_FOR_COOKIE                    = 6;
    
    public static final int     MAX_PASSWORD_LENGTH                         = 20;
    public static final int     SHA_PASSWORD_LENGTH                         = 40;
    
    public static final String  ACTION_ADD                                  = "Add";
    public static final String  ACTION_MODIFY                               = "Modify";
    public static final String  ACTION_REMOVE                               = "Remove";
    public static final String  ACTION_SUB_TERMINATE                        = "SubTerminate";
    public static final String  ACTION_SUB_ACTIVE                           = "SubActive";

    public static final int     AUTHENTICATION_FAIL                         = 2;
    public static final int     AUTHENTICATION_PASS                         = 1;

    public static final String  BLANK_STRING                                = "";

    public static final String  DEFAULT_COMPOSITION_CODE                    = "1";
    public static final String  DEFAULT_END_DATE                            = "9999-12-31T23:59:59";
    public static final String  DEFAULT_END_DAY                             = "9999-12-31";

    public static final String  FAIL                                        = "0";
    public static final int     INITIAL_INT_VALUE                           = 0;

    public static final String  LOOKUP_TYPE_BASE                            = "Base";
    public static final String  LOOKUP_TYPE_BASE_TARGET                     = "BaseAndTarget";
    public static final String  LOOKUP_TYPE_TARGET                          = "Target";

    public static final String  NO                                          = "0";
    public static final String  NO_FLAG                                     = "N";
    public static final String  NULL_STRING                                 = null;

    public static final String  RELATION_SERV2SUBS                          = "ServiceDomainToSubscription";
    public static final String  RELATION_SUB2CREDENTIAL                     = "SubscriptionToCredential";
    public static final String  RELATION_SUB2PARTY                          = "SubscriptionToParty";
    public static final String  RELATION_SUB2PARTY_HIS_EXCLUSION            = "SubscriptionToPartyHisExc";
    public static final String  RELATION_SUB2RESOURCE                       = "SubscriptionToResource";
    public static final String  RELATION_SUBSV2SUBOFF                       = "SubscriptionServiceVariantToSubscriptionOffer";

    public static final int     UPDATE_PASSWORD                             = 3;

    public static final String  YES                                         = "1";
    
    public static final String  FORCE_END_RESPONSE                          = "96"; 
    public static final String  TIMEOUT_RESPONSE                            = "31";
    public static final String  SUCCESS_RESPONSE                            = "0";
    public static final String  RETRY_RESPONSE                              = "3";
    public static final String  EXCEPTION_RESPONSE                          = "99";

    public static final String  OLLEH_WIFI_TEMP                             = "OLLEHWIFITEMP";

    public static final String  DEFAULT_VIRTUAL_OFFER_SOURCE_SYSTEM_BIND_ID         = "VIROFFERWEB";
    public static final String  DEFAULT_UCLOUD_VIRTUAL_OFFER_SOURCE_SYSTEM_BIND_ID  = "VIROFFERUCLOUD";

    public static final String  ENCODINGCHARSET                             = "Cp1252";
        
    public static final String  EXCEPTION_ERROR_CODE                        = "0000";
    public static final String  SHR_ETC_EXCEPTION_ERROR_CODE                = "0001";
    public static final String  BPEL_ETC_EXCEPTION_ERROR_CODE               = "0003";
    public static final String  NOT_EXIST_EXCEPTION_ERROR_CODE              = "0010";
    public static final String  DEFAULT_EXCEPTION_ERROR_CODE                = "0011";
    public static final String  JNDI_DATA_SOURCE_ERROR_CODE                 = "9999";
    public static final String  SDP_UNDEFINED_EXCEPTION_ERROR               = "9910";
    public static final String  NOT_EXIST_PARTY_TYPE_ID_ERROR               = "0012";
    
    public static final String SVC_FEATUREID_CNIPX                          = "CNIPX";
    
    public static final String  ZEROSTRING                                  = "0";
    
    
    /**************************************************************************
    OutgoingDbActionType
    ====================
    **************************************************************************/
    interface OutgoingDbActionType {
        public static final String  INSERT              = "I";
        public static final String  UPDATE              = "U";
        public static final String  DELETE              = "D";
    }

    /**************************************************************************
    SubscrProfileAttributeDetailId
    ====================
    **************************************************************************/
    interface SubscrProfileAttributeDetailId {
        public static final String  LST_COM_ACTV_DATE               = "Last_Status_Updated_Date";
        public static final String  SUB_STATUS_DATE                 = "Device_Activation_Date";
    }

    /**************************************************************************
     * BssOrderEventCode ====================
     **************************************************************************/
    interface BssOrderEventCode {
        public static final String NEW_INSTALL        = "NEW INSTALL";
        public static final String MODIFY_ETC_ORDER   = "MODIFY ETC ORDER";
        public static final String TERMINATE_REVERSAL = "TERMINATE REVERSAL";
        public static final String TERMINATE          = "TERMINATE";
        public static final String SUSPEND            = "SUSPEND";
        public static final String RESUME             = "RESUME";
        public static final String TRANSFER_OWNER     = "TRANSFER OWNER";
        public static final String MODIFY_NUMBER      = "MODIFY NUMBER";
        public static final String CHANGE_TERMINAL    = "CHANGE TERMINAL";
        public static final String CHANGE_USIM        = "CHANGE USIM";
        public static final String MODIFY_PRODUCTVAS  = "MODIFY PRODUCTVAS";
        public static final String RE_INSTALL         = "RE INSTALL";
        public static final String CHANGE_UTAS        = "CHANGE UTAS";
    }

    /**************************************************************************
    ContactIndicateCd
    ====================
    **************************************************************************/
    interface ContactIndicateCd {
        public static final String  ACCOUNT_ID              = "01";
        public static final String  SUBSCRIPTION_ID         = "02";
        public static final String  PARTY_ID                = "03";
    }
    
    /**************************************************************************
    SSV의 Profile CAPRI_MIG_TAB_TYPE
    ====================
    **************************************************************************/
    interface CapriMigTabType {
        public static final String SFP     = "SFP";
        public static final String SCF     = "SCF";
        public static final String ALL     = "ALL";
    }

    /**************************************************************************
    SSV의 resource SourceSystemCode
    ====================
    **************************************************************************/ 
    interface SourceSystemCode {
        public static final String GENESIS     = "20";
        public static final String ICIS        = "01";
    }
    
    /**************************************************************************
    SSV의 resource SourceSystemCode
    ====================
    **************************************************************************/ 
    interface CustomerType {
        public static final String PERSONAL     = "I";
        public static final String PRIVATE_ENT  = "O";
        public static final String PUBLIC_INST  = "G";
        public static final String CORPORATE_ENT= "B";
        public static final String CORPORATION  = "J";
        public static final String NOPERSONAL   = "E";
    }
    /**************************************************************************
    Subscription 의 StatusTypCode
    ====================
    **************************************************************************/
    interface SubStatusTypeCd {
        public static final String USE          = "A";
        public static final String PAUSE        = "S";
        public static final String CANCELLATION = "C";
    }
    
    /**************************************************************************
    Resource 의 StatusTypCode
    ====================
    **************************************************************************/
    interface ResourceStatusTypeCd {
        public static final String STANDBY  = "01";
        public static final String USE      = "02";
        public static final String PAUSE    = "06";
        public static final String TERMINATE= "04";
        public static final String TRASH    = "03";
        public static final String LOST     = "05";
    }
    
    /**************************************************************************
    CredentialRelnTypeCd
    ====================
    **************************************************************************/
    interface CredRelnTypeCd {
        public static final String CREDT_RELN_TYPE_CD_B = "B";
    }
    /**************************************************************************
    CredentialTypeCd
    ====================
    **************************************************************************/
    interface CredTypeCd {
        public static final String CREDT_TYPE_CD_W = "W";
    }
    /**************************************************************************
    FeatureCode
    ====================
    **************************************************************************/
    interface FeatureCode {
        public static final String PLUGCS   = "PLUGCS";
        public static final String PLHGCS   = "PLHGCS";
        public static final String NEWGCS   = "NEWGCS";
        public static final String SCFU     = "SCFU";
        public static final String MSCFU    = "MSCFU";
        public static final String CFUPLUS  = "CFUPLUS";
        
    }
    /**************************************************************************
    Profile Attribute DetailName
    ====================
    **************************************************************************/
    interface ProfileAttrDetailName {
        public static final String SMS_GUIDE_YN         = "SMS_Guide_YN";
        public static final String VOICE_SUPPORT_YN     = "Voice_Support_YN";
        public static final String LANG_SUPPORT         = "Lang_Support";
        public static final String CAPRI_MIG_TAB_TYPE   = "Capri_Mig_Tab_Type";
        public static final String CUSTOMER_CLASS_CD    = "Customer_Class_CD";
        public static final String AGENT_CD             = "Agent_CD";
        public static final String PREPAID_PLAN_TYPE_CD = "PrePaid_plan_Type_CD";
        public static final String REALNAME_CHECK_DATE = "RealName_Check_Date";
        public static final String PRODUCT_HIGH_LEVEL_CATEGORY_CD = "Product_High_Level_Category_CD";
        public static final String TIMECODI_PASSWORD    = "TimeCodi_Password";
        public static final String KISWIN_PASSWORD      = "KidWindow_Password";
    }
    /**************************************************************************
    Y/N
    ====================
    **************************************************************************/
    interface YN {
        public static final String Y = "Y";
        public static final String N = "N";
    }
    /**************************************************************************
    SubscpnSourceTypeCd
    ====================
    **************************************************************************/    
    interface SubscpnSourceTypeCd {
        public static final String BITBSS_SUBSCPN_ID    = "01";
        public static final String ICIS_SA_ID           = "02";
        public static final String NSTEP_CN             = "03";
        public static final String JUICE_NCN            = "04";
        public static final String NSTEP_OPEN_CN        = "05";
        public static final String SDP_SUBSCPN_ID       = "06";
    }
    /**************************************************************************
    SearchTypeCd
    ====================
    **************************************************************************/    
    interface SearchTypeCd {
        final String SEARCH_TYPE_01   = "01";
        final String SEARCH_TYPE_02   = "02";
        final String SEARCH_TYPE_03   = "03";
        final String SEARCH_TYPE_99   = "99";
    }
    
    /* KT */
    public static final String MARKET_KUBUN_KT              = "KT";
    
    /* AESKEY */
    public static final boolean LOCAL_TEST_AESKEY           = false;
    
    /* CAPRI AESKEY */
    public static final String CAPRI_AESKEY                 = "CAPRI.AESKEY";
    
    /* 3G LastRelaySystemCond */
    public static final String  LAST_RELAY_SYS_CODE_3G      = "24";
    
    /* All Search Define */
    public static final String  SEARCH_ALL                  = "ALL";
    
    /* Subscpn Standart Type cd : B */
    public static final String  SUBSCPN_STANDARD_TYPE_CD_B  = "B";
    
    /*  PSTN prodtypecode   */
    public static final String PROD_TYPE_SUB                = "1";

    /* Subscpn Status Cd : 03(해지) */
    public static final String CANCELLATION                 = "03";
    
    /* Genesis Contact Usage Type Cd */
    public static final String  BSS_CONTACT_USAGE_TYPE_CD   = "08";
    
    public static final String NOT_CHAGE_CD                 = "NA";
    
    public static final String CANCEL_DEFAULT_USERNAME      = "99999999999";
    
    public static final String CONV_SUBSCPN_TYPE_CD         = "Conv_Subscpn_type_CD";
    
    /* CP사별 계약건수 : 마이올레 */
    public static final long MYOLLEH_SUBSCPN_CNT = 50;
    
    /* CP사별 계약건수 : OTM */
    public static final long OTM_SUBSCPN_CNT = 50;
    
    /* KT법인 사업자번호 */
    public static final String KT_PARTY_IDTF_NUMBER = "KT_PARTY_IDENTIFICATION_NUMBER";

    
    /**************************************************************************
    UnExpectSuccessCode
    ====================
    **************************************************************************/
    interface UnExpectSuccessCode {
        public static final String NEW_INSTALL      = "01";
        public static final String TERMINATE        = "02";        
        public static final String SUSPEND          = "03";
        public static final String CHANGE_RESOURCE  = "04";
        public static final String CHANGE_CREDENTIAL= "05";
        public static final String CHANGE_OWNER     = "06";
        public static final String CHANGE_PRODUCTVAS= "07";
        public static final String CHANGE_ETC       = "08";
        public static final String CHANGE_UTAS      = "09";
        public static final String CHANGE_PARTY     = "10";
        public static final String CHANGE_ACCOUNT   = "11";
        public static final String CHANGE_SUBSCPN_ACCOUNT = "12";
    }   
    
    /**************************************************************************
    The Interface SubscriptionRelnTypeCode.
    ====================
    **************************************************************************/
    interface SubscriptionRelnTypeCode {
        int SUBSCRIPTIONRELNTYPECODE_1      = 1;
        int SUBSCRIPTIONRELNTYPECODE_13     = 13;
        int SUBSCRIPTIONRELNTYPECODE_99     = 99;
    }
    
    /**************************************************************************
    SourceTypeCode, SourceValue Set
    ====================
    **************************************************************************/
    interface SourceTypeCdValueSet {
        public static final String NSTEP_SUB        = "01";
        public static final String NSTEP_ACC        = "02";
        public static final String NSTEP_PARTY      = "03";
        public static final String BSS_SUB          = "04";
        public static final String BSS_ACC          = "05";
        public static final String BSS_PARTY        = "06";
     }

    /**************************************************************************
    SourceTypeCode, SourceValue Set
    01  :  서비스가입(부가서비스 가입, PP 변경시 변경 후 PP)
    04 :  서비스해지(부가서비스 해지, PP 변경시 변경 전 PP)
    03  :  서비스변경 ( 부가서비스 파라메터 변경(ex GCS 안내 대상번호 변경))
    02 : 변경 없음(기존에 유지되는 부가서비스 연동시)

    ====================
    **************************************************************************/
    interface SSVStatusCode {
        public static final String NEW_SERVICE      = "01";
        public static final String NO_CHANGE        = "02";
        public static final String CHANGE_SERVICE   = "03";
        public static final String TERMINATE_SERVICE= "04";
     }

    /**************************************************************************
    AuthTypeCode
    01  :  OLLEH Type
    02  :  TIME_CODI Type
    03  :  KIDS_WINDOWS Type
    ====================
    **************************************************************************/
    interface AuthTypeCode {
        public static final String OLLEH            = "01";
        public static final String TIME_CODI        = "02";
        public static final String KIDS_WINDOW      = "03";
    }
    
    /**************************************************************************
    SSV Source System Bind Id 
    #TIMECO  :  TIME_CODI Type
    #KIDSWI  :  KIDS_WINDOWS Type
    ====================
    **************************************************************************/
    interface SSVSourceSystemBindId {
        public static final String TIMECO       = "#TIMECO";
        public static final String KIDSWI       = "#KIDSWI";
    }
    
    /**************************************************************************
    GoogleDCB Limittarget
    ====================
    **************************************************************************/
    interface googleDCBLimitTarget {    
        public static final String DCB_SDP_NOOUT            = "Dcb_SDP_NoOUT";  
        public static final String DCB_SDP_TRANSFEROWNER    = "Dcb_SDP_TransferOwner";  
        public static final String DCB_SDP_MODIFYNUMBER     = "Dcb_SDP_ModifyNumber";   
        public static final String DCB_SDP_TERMINATE        = "Dcb_SDP_Terminate";  
        public static final String DCB_SDP_INTNETWORK       = "Dcb_SDP_IntNetwork"; 
        public static final String DCB_SDP_PREPAID          = "Dcb_SDP_Prepaid";    
        public static final String DCB_SDP_ENTERPRISE       = "Dcb_SDP_Enterprise"; 
        public static final String DCB_SDP_MVNO             = "Dcb_SDP_MVNO";   
        public static final String DCB_SDP_UNDERAGE         = "Dcb_SDP_Underage";   
        public static final String DCB_SDP_LIMITDATA        = "Dcb_SDP_LimitData";  
        public static final String DCB_SDP_TEMPSUSPEND      = "Dcb_SDP_TempSuspend";    
        public static final String DCB_SDP_SCTO             = "Dcb_SDP_SCTO";
        public static final String DCB_SDP_TRANSACTIONLIMIT = "Dcb_SDP_TransactionLimit";
     }
    
    /**************************************************************************
    GoogleDCB ResultMessage
    ====================
    **************************************************************************/
    interface googleDCBResultMessage {
        public static final String SUCCESS                      = "SUCCESS";    
        public static final String CHARGE_EXCEEDS_LIMIT         = "CHARGE_EXCEEDS_LIMIT";   
        public static final String INSUFFICIENT_FUNDS           = "INSUFFICIENT_FUNDS"; 
        public static final String INVALID_BILLING_AGREEMENT    = "INVALID_BILLING_AGREEMENT";  
        public static final String INVALID_CURRENCY             = "INVALID_CURRENCY";       
        public static final String RETRIABLE_ERROR              = "RETRIABLE_ERROR";        
        public static final String GENERAL_DECLINE              = "GENERAL_DECLINE";        
        public static final String ACCOUNT_ON_HOLD              = "ACCOUNT_ON_HOLD";        
        public static final String SUBSCRIPTION_CANCELLED       = "SUBSCRIPTION_CANCELLED";     
        public static final String NO_LONGER_PROVISIONED        = "NO_LONGER_PROVISIONED";      
        public static final String INVALID_USER                 = "INVALID_USER";       
        public static final String INVALID_TOS                  = "INVALID_TOS";        
        public static final String INVALID_PASSPHRASE_FAILURE   = "INVALID_PASSPHRASE_FAILURE";     
        public static final String GENERAL_FAILURE              = "GENERAL_FAILURE";        
        public static final String ADDRESS_UNKNOWN              = "ADDRESS_UNKNOWN";
        public static final String GENERAL              = "GENERAL";
        public static final String TBD              = "TBD";
        public static final int DCB_VERSION             = 3;
     }
    
    /**************************************************************************
    GoogleDCB Api Name
    ====================
    **************************************************************************/
    interface googleDCBApiName {
        public static final String DCBApproveSubscriber     = "DCBApproveSubscriber";
        public static final String DCBAUTH                  = "DCBAuth";    
        public static final String DCBGETPROVISIONING       = "DCBGetProvisioning";     
        public static final String DCBGetSubscriberAddress  = "DCBGetSubscriberAddress";
     }
    
    
    /**************************************************************************
    GoogleDCB DCB ParamtersName
    ====================
    **************************************************************************/
    interface googleDCBParamtersName {
        public static final String APPROVESUBSCRIBERMESSAGE = "approveSubscriberMessage";
        public static final String CELLPHONENUMBER          = "CellPhoneNumber";
        public static final String GOOGLEUSERTOKEN          = "GoogleUserToken";
        public static final String KR                       = "KR";
    }
    
    /**************************************************************************
    GoogleDCB DCB State RE
    ====================
    **************************************************************************/
    interface googleDCBState {
        public static final String GANGWON              = "강원도";
        public static final String GYEONGGI             = "경기도";
        public static final String GYENGSANGNAM         = "경상남도";
        public static final String GYENGSANGBUK         = "경상북도";
        public static final String GWANGJU              = "광주광역시";
        public static final String DAEGU                = "대구광역시";
        public static final String DAEJEON              = "대전광역시";
        public static final String BUSAN                = "부산광역시";
        public static final String SEOUL                = "서울특별시";
        public static final String SEOJONG              = "세종특별자치시";
        public static final String ULSAN                = "울산광역시";
        public static final String INCHEON              = "인천광역시";
        public static final String JEOLLANAM            = "전라남도";
        public static final String JEOLLABUK            = "전라북도";
        public static final String JEJU                 = "제주특별자치도";
        public static final String CHUNGCHEONGNAM       = "충청남도";
        public static final String CHUNGCHEONGBUK       = "충청북도";
    }
    
    /**************************************************************************
    Olleh Credential Type Cd
    ====================
    **************************************************************************/
    interface ollehCredtTypeCd {
        public static final String OLLEHEMAIL           = "01";
        public static final String OPENEMAIL            = "02";
        public static final String NOTOLLEHEMAIL        = "03";
    }  
    
    /**************************************************************************
    Party Subscpn Status CD
    ====================
    **************************************************************************/
    interface partySubscpnStatusCd {
        public static final String NORMAL           = "01";
        public static final String SUSPEND          = "02";
        public static final String TERMINATE        = "03";
        public static final String CONVERSION       = "04";
        public static final String NOUSE            = "05";
    }     
}