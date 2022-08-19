package view.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.jbo.Row;
import oracle.jbo.ViewCriteria;
import oracle.jbo.server.ViewObjectImpl;

public class LoginBean implements Serializable {
    private RichInputText empIdBind;
    private RichInputText passwordBind;

    public LoginBean() {
    }

    public String loginAction() {
        System.out.println("entered Login Action");
                String EmpId = this.getEmpIdBind().getValue().toString();
                String Password = this.getPasswordBind().getValue().toString();
                System.out.println("EmpId::"+EmpId+";;Password::"+Password);
                
                DCBindingContainer dc1 = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                ViewObjectImpl vo1 = (ViewObjectImpl)dc1.findIteratorBinding("EmployeesVOIterator").getViewObject();
                ViewCriteria vc = vo1.getViewCriteria("LoginVc1");
                vo1.setNamedWhereClauseParam("BEmpId", EmpId);
                vo1.setNamedWhereClauseParam("BPassword", Password);
                vo1.applyViewCriteria(vc);
                vo1.executeQuery();
                System.out.println("results::"+ vo1.getRowCount());
                if(vo1.hasNext()){
                    Row r1 = vo1.next();
                    System.out.println("Role::"+r1.getAttribute("Role"));
                    System.out.println("success");
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    HttpSession session = (HttpSession)ctx.getExternalContext().getSession(true);
                    session.setAttribute("employeeId", EmpId);
                    session.setAttribute("role", r1.getAttribute("Role"));
                    return "okgo";
                }else{
                        FacesContext context = FacesContext.getCurrentInstance();
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, " Wrong UserId or Password..", null));
                    return null;}
    }

    public void setEmpIdBind(RichInputText empIdBind) {
        this.empIdBind = empIdBind;
    }

    public RichInputText getEmpIdBind() {
        return empIdBind;
    }

    public void setPasswordBind(RichInputText passwordBind) {
        this.passwordBind = passwordBind;
    }

    public RichInputText getPasswordBind() {
        return passwordBind;
    }
}
