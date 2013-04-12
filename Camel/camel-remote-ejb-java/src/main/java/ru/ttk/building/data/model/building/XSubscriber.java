package ru.ttk.building.data.model.building;

import ru.ttk.building.data.model.BDocument;
import ru.ttk.building.data.model.core.XReference;
import ru.ttk.building.data.model.core.dictionary.XDictionaryItem;
import ru.ttk.building.model.type.PaymentMethod;

import java.util.HashSet;
import java.util.Set;

public class XSubscriber extends BDocument {

    private static final long serialVersionUID = 478268290217325814L;

    // Физическое лицо
    private String lastName;
    private String firstName;
    private String middleName;

    private XPassport passport;

    // ИП + Компания
    //    @LazyCollection(LazyCollectionOption.FALSE) // HIBERNATE feature
    //    @OneToMany(mappedBy = "subscriber")
    //    @Fetch(value = FetchMode.SUBSELECT)
    //    private Set<XContact> contacts;
    // Let's simplify a little bit
    private String phone;
    private String phone2;

    private String email;
    private String email2;

    private String fioOfContactMan;
    private XCompany company;
    private PaymentMethod paymentMethod;
    private XReference<XDictionaryItem> connectionStatus;
    private XReference<XDictionaryItem> clientType;
    private Set<XUnit> units;
    private String contractNumber;
    private String comments;

    public XSubscriber() {
        super();

        this.lastName = "";
        this.firstName = "";
        this.middleName = "";

        this.passport = new XPassport();

        this.phone = "";
        this.phone2 = "";

        this.email = "";
        this.email2 = "";

        this.fioOfContactMan = "";

        this.company = new XCompany();

        this.paymentMethod = PaymentMethod.ADVANCE_PAYMENT;

//        private XReference<XDictionaryItem> connectionStatus;
//        private XReference<XDictionaryItem> clientType;

        this.units = new HashSet<XUnit>();

        this.contractNumber = "";
        this.comments = "";
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public XPassport getPassport() {
        return passport;
    }

    public void setPassport(XPassport passport) {
        this.passport = passport;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getFioOfContactMan() {
        return fioOfContactMan;
    }

    public void setFioOfContactMan(String fioOfContactMan) {
        this.fioOfContactMan = fioOfContactMan;
    }

    public XCompany getCompany() {
        return company;
    }

    public void setCompany(XCompany company) {
        this.company = company;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public XReference<XDictionaryItem> getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(XReference<XDictionaryItem> connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public XReference<XDictionaryItem> getClientType() {
        return clientType;
    }

    public void setClientType(XReference<XDictionaryItem> clientType) {
        this.clientType = clientType;
    }

    public Set<XUnit> getUnits() {
        return units;
    }

    public void setUnits(Set<XUnit> units) {
        this.units = units;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    private String makeFIO(){
        return this.lastName + " " + this.firstName + " " + this.middleName;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

//    public String getFullCompanyName() {
//        return company.getFullCompanyName();
//    }
//
//    public void setFullCompanyName(String fullCompanyName) {
//        company.setFullCompanyName(fullCompanyName);
//    }

    @Override
    public String toString(){
        return " type:" + this.clientType.getName() + " paymentMethod:" + this.paymentMethod + " fio:" + this.makeFIO();
    }

}
