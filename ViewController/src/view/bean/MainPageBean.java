package view.bean;

import java.io.IOException;

import java.util.regex.*;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.server.ViewObjectImpl;

public class MainPageBean {
    private RichInputText currnetPassBind;
    private RichInputText newPassBind;
    private RichInputText conformPassBind;
    private RichPopup r1;
    private RichPopup r2;
    private RichTable leavesPendingTabBind;

    public MainPageBean() {
    }

    public void setCurrnetPassBind(RichInputText currnetPassBind) {
        this.currnetPassBind = currnetPassBind;
    }

    public RichInputText getCurrnetPassBind() {
        return currnetPassBind;
    }

    public void setNewPassBind(RichInputText newPassBind) {
        this.newPassBind = newPassBind;
    }

    public RichInputText getNewPassBind() {
        return newPassBind;
    }

    public void setConformPassBind(RichInputText conformPassBind) {
        this.conformPassBind = conformPassBind;
    }

    public RichInputText getConformPassBind() {
        return conformPassBind;
    }

    public void savePassWord(ActionEvent actionEvent) {
       System.out.println("inside Change Passsword menthod");
       String curPass = this.getCurrnetPassBind().getValue().toString();
       String newPass = this.getNewPassBind().getValue().toString();
       String confPass = this.getConformPassBind().getValue().toString();
       System.out.println("Entered CurrentPassword::"+curPass+" ;;NewPassword:: "+newPass+
                          " ;;ConformPassword::  "+confPass);
        DCBindingContainer dc1 = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        ViewObjectImpl vo1 = (ViewObjectImpl)dc1.findIteratorBinding("LoggedEmpVOIterator").getViewObject();
        Row currentRow = vo1.getCurrentRow();
        String presentPass = currentRow.getAttribute("Password").toString();
        System.out.println("Present Password::"+presentPass);
        if(!presentPass.equals(curPass)){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, " You Current Password is Incorrect..", null));
            resetPasswordPopup();
            return;
        }
        if(!confPass.equals(newPass)){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, " New Password and Conform Password Didnot Match..", null));
            resetPasswordPopup();
            return;
        }
        
        System.out.println(isValidPassword(newPass));
        if(isValidPassword(newPass)){
            currentRow.setAttribute("Password", newPass);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, " Password Changed Sucessfully ..", null));
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
                    OperationBinding ob = bindings.getOperationBinding("Commit");
                    ob.execute();
            System.out.println(currentRow.getAttribute("Password"));
            resetPasswordPopup();
        }else{
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, " Password did not match the Required Criteria..", null));
            resetPasswordPopup();
            return;
        }
        
    }
    
    // Function to reset the Password fields
    public void resetPasswordPopup(){
        this.getConformPassBind().resetValue();
        this.getCurrnetPassBind().resetValue();
        this.getNewPassBind().resetValue();
    }
    
    // Function to validate the password.
        public static boolean isValidPassword(String password)
        {
            // Regex to check valid password.
            String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";
    
            // Compile the ReGex
            Pattern p = Pattern.compile(regex);
      
            // If the password is empty
            // return false
            if (password == null) {
                return false;
            }
      
            // Pattern class contains matcher() method
            // to find matching between given password
            // and regular expression.
            Matcher m = p.matcher(password);
      
            // Return if the password
            // matched the ReGex
            return m.matches();
        }

    public void setR1(RichPopup r1) {
        this.r1 = r1;
    }

    public RichPopup getR1() {
        return r1;
    }

    public void addEmployeeAL(ActionEvent actionEvent) {
        System.out.println("Entered CreateInsertWith Popup");
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding ob = bindings.getOperationBinding("CreateInsert");
        ob.execute();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        this.getR1().show(hints);
        System.out.println("----");
    }

    public void setR2(RichPopup r2) {
        this.r2 = r2;
    }

    public RichPopup getR2() {
        return r2;
    }

    public void applyLeavePopupAL(ActionEvent actionEvent) {
        System.out.println("Entered CreateInsertWith Popup leaves");
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding ob = bindings.getOperationBinding("CreateInsert1");
        ob.execute();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        this.getR2().show(hints);
        System.out.println("----");
    }

    public void applyLeaveAL(ActionEvent actionEvent) {
        DCBindingContainer dc1 = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        ViewObjectImpl vo1 = (ViewObjectImpl)dc1.findIteratorBinding("LoggedEmpVOIterator").getViewObject();
        Row currentRow = vo1.getCurrentRow();
        int availableLeaves = Integer.parseInt(currentRow.getAttribute("Leavesavailable").toString());
        ViewObjectImpl vo2 = (ViewObjectImpl)dc1.findIteratorBinding("EmpLeavesVOIterator").getViewObject();
        Row leavesRow = vo2.getCurrentRow();
        int appliedLeaves = Integer.parseInt(leavesRow.getAttribute("Days").toString());
        System.out.println("Availabe Leaves ::"+availableLeaves);
        System.out.println("Applied Leaves ::"+appliedLeaves);
        if(appliedLeaves>availableLeaves){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, " You cannot Apply Leaves more than your Available Leaves..", null));
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding ob = bindings.getOperationBinding("Rollback");
            ob.execute();
            return;
        }else{
            currentRow.setAttribute("Leavesavailable", availableLeaves-appliedLeaves);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, " Leave Applied Sucessfully and Sent For Approval..", null));
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding ob = bindings.getOperationBinding("Commit");
            ob.execute();
        }
        
        
    }

    public void approvingLeaveAL(ActionEvent actionEvent) {
        
        DCBindingContainer dc1 = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        ViewObjectImpl vo1 = (ViewObjectImpl)dc1.findIteratorBinding("LeavesForApprovalIterator").getViewObject();
        Row currentRow = vo1.getCurrentRow();
        System.out.println("Applied Leaves dates"+currentRow.getAttribute("Days"));
        currentRow.setAttribute("Status", "A");
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding ob = bindings.getOperationBinding("Commit");
        ob.execute();
        // refreshing the table
        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
        adfFacesContext.addPartialTarget(this.getLeavesPendingTabBind());
    }

    public void rejectingLeaveAL(ActionEvent actionEvent) {
        DCBindingContainer dc1 = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        ViewObjectImpl vo1 = (ViewObjectImpl)dc1.findIteratorBinding("LeavesForApprovalIterator").getViewObject();
        Row currentRow = vo1.getCurrentRow();
        System.out.println("Applied Leaves dates"+currentRow.getAttribute("Days"));
        currentRow.setAttribute("Status", "R");
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding ob = bindings.getOperationBinding("Commit");
        ob.execute();
        // refreshing the table
        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
        adfFacesContext.addPartialTarget(this.getLeavesPendingTabBind());
    }

    public void setLeavesPendingTabBind(RichTable leavesPendingTabBind) {
        this.leavesPendingTabBind = leavesPendingTabBind;
    }

    public RichTable getLeavesPendingTabBind() {
        return leavesPendingTabBind;
    }

    public String logOutAction() {
        try {
            FacesContext fctx = FacesContext.getCurrentInstance();
            HttpSession session =
                (HttpSession)fctx.getExternalContext().getSession(true);
            if (session != null) {
                session.invalidate();
            }
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect("/faces/LoginView.jspx");
        } catch (Exception e) {
            // TODO: Add catch code
            System.out.println("error in logout::"+e);
            e.printStackTrace();
        }
        return "goBack";
    }

    public void logOutAL(ActionEvent actionEvent) {
        try {
            FacesContext fctx = FacesContext.getCurrentInstance();
            HttpSession session =
                (HttpSession)fctx.getExternalContext().getSession(true);
            if (session != null) {
                session.invalidate();
            }
           
        } catch (Exception e) {
            // TODO: Add catch code
            System.out.println("error in logout::"+e);
            e.printStackTrace();
        }
    }
    
}
