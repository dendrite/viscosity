package ru.ttk.building.data.model.building;


import java.io.Serializable;

/**
 *
 */
public class XCompany implements Serializable {

    // Юридическое лицо
    private String businessNameHolder;  // Ф.И.О. лица, принимающего решение/влияющего на принятие решения.

    private String shortCompanyName;    // Сокращенное наименование	АКБ «Абсолют Банк» (ЗАО)
    private String fullCompanyName;     // Полное наименование	Акционерный коммерческий банк «Абсолют Банк» (закрытое акционерное общество)
    private String worldCompanyName;    // Фирменное наименование, используемое во внешнеэкономической деятельности	Absolut Bank

    private String juridicalAddress;    // Юридический адрес	127051, г. Москва, Цветной бульвар, д. 18
    private String locationAddress;     // Местонахождение	127051, г. Москва, Цветной бульвар, д. 18
    private String postAddress;         // Почтовый адрес	127051, г. Москва, Цветной бульвар, д. 18

    private String companyPhone;        // телефон
    private String fax;                 // факс

    private String INN;                 // Идентификационный номер налогоплательщика (ИНН / КПП)	7736046991 / 775001001

    private String BIK;                 // БИК
    private String OKVED;               // ОКВЭД	65.12
    private String OKPO;                // ОКПО	17527415
    private String OGRN;                // ОГРН	1027700024560
    private String OKATO;               // ОКАТО	45286570000
    private String KPP;                 // КПП

    private String currentAccount;      // Расчетный счет
    private String account;             // Платежные реквизиты	Корр. счет 30101810500000000976 в ОПЕРУ Московского ГТУ Банка России БИК 044525976,
    private String bankName;            // Название банка

    private String siteUrl;




//    // Параметры для показа
//
//    -private String fullCompanyName;     // Полное наименование	Акционерный коммерческий банк «Абсолют Банк» (закрытое акционерное общество)
//
//    -private String juridicalAddress;    // Юридический адрес	127051, г. Москва, Цветной бульвар, д. 18
//
//    -private String companyPhone;        // телефон
//    -private String fax;                 // факс
//
//    -private String INN;                 // Идентификационный номер налогоплательщика (ИНН / КПП)	7736046991 / 775001001
//
//    -private String BIK;                 // БИК
//
//    -private String KPP;                 // КПП
//
//    -private String currentAccount;      // Расчетный счет
//    -private String account;             // Платежные реквизиты	Корр. счет 30101810500000000976 в ОПЕРУ Московского ГТУ Банка России БИК 044525976,
//    -private String bankName;             // Название банка
//
//    -private String siteUrl;


    public XCompany(){
        this.businessNameHolder = "";

        this.shortCompanyName = "";
        this.fullCompanyName = "";
        this.worldCompanyName = "";

        this.juridicalAddress = "";
        this.locationAddress = "";
        this.postAddress = "";

        this.companyPhone = "";
        this.fax = "";

        this.INN = "";

        this.BIK = "";
        this.OKVED = "";
        this.OKPO = "";
        this.OGRN = "";
        this.OKATO = "";
        this.KPP = "";

        this.bankName = "";

        this.currentAccount = "";
        this.account = "";

        this.siteUrl = "";
    }

    public String getBusinessNameHolder() {
        return businessNameHolder;
    }

    public void setBusinessNameHolder(String businessNameHolder) {
        this.businessNameHolder = businessNameHolder;
    }

    public String getShortCompanyName() {
        return shortCompanyName;
    }

    public void setShortCompanyName(String shortCompanyName) {
        this.shortCompanyName = shortCompanyName;
    }

    public String getFullCompanyName() {
        return fullCompanyName;
    }

    public void setFullCompanyName(String fullCompanyName) {
        this.fullCompanyName = fullCompanyName;
    }

    public String getWorldCompanyName() {
        return worldCompanyName;
    }

    public void setWorldCompanyName(String worldCompanyName) {
        this.worldCompanyName = worldCompanyName;
    }

    public String getJuridicalAddress() {
        return juridicalAddress;
    }

    public void setJuridicalAddress(String juridicalAddress) {
        this.juridicalAddress = juridicalAddress;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getPostAddress() {
        return postAddress;
    }

    public void setPostAddress(String postAddress) {
        this.postAddress = postAddress;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getINN() {
        return INN;
    }

    public void setINN(String INN) {
        this.INN = INN;
    }

    public String getBIK() {
        return BIK;
    }

    public void setBIK(String BIK) {
        this.BIK = BIK;
    }

    public String getOKVED() {
        return OKVED;
    }

    public void setOKVED(String OKVED) {
        this.OKVED = OKVED;
    }

    public String getOKPO() {
        return OKPO;
    }

    public void setOKPO(String OKPO) {
        this.OKPO = OKPO;
    }

    public String getOGRN() {
        return OGRN;
    }

    public void setOGRN(String OGRN) {
        this.OGRN = OGRN;
    }

    public String getOKATO() {
        return OKATO;
    }

    public void setOKATO(String OKATO) {
        this.OKATO = OKATO;
    }

    public String getKPP() {
        return KPP;
    }

    public void setKPP(String KPP) {
        this.KPP = KPP;
    }

    public String getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(String currentAccount) {
        this.currentAccount = currentAccount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public String toString(){
        return " company:" + this.shortCompanyName + " site:" + this.siteUrl + " postAddress:" + this.postAddress;
    }
}
