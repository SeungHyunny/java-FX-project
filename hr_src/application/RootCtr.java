package application;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import common.TableViewFactory;
import dao.DepartmentsDAO;
import dao.EmployeesDAO;
import dao.Job_historyDAO;
import dao.JobsDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import service.HrService;
import vo.Departments;
import vo.Employees;
import vo.Jobs;

public class RootCtr implements Initializable {
	//
	private EmployeesDAO employeesdao;
	private int employee_id;
	private JobsDAO jobsdao;
	private DepartmentsDAO dedao;
	private Job_historyDAO job_historydao;
	private String job_id;
	private String hire_date;
	
	DepartmentsDAO ddao = new DepartmentsDAO();
	EmployeesDAO edao = new EmployeesDAO();
	Job_historyDAO jhdao = new Job_historyDAO();
	JobsDAO jdao = new JobsDAO();
	HrService service = new HrService();
	TableView table = TableViewFactory.getTable(Employees.class);
	

	@FXML
	void clear(ActionEvent event) {
		table.getItems().clear();
	}
	
	    // 1. 주어진 기간의 입사자 목록찾기
	    @FXML
	    private TextField txtStart;

	    @FXML
	    private TextField txtEnd;

	    @FXML
	    private Button btnDateList;
	    
	    @FXML
	    void handleBtnDateList(ActionEvent event) {
	    	Date a = str2Date(txtStart.getText());
	    	Date b = str2Date(txtEnd.getText());
	    	List<Employees> result = service.getEmpListByDate(a,b);
	    	clear(new ActionEvent());
	    	table.getItems().addAll(result);
	    	
	    }

	    // 2. 급여순으로 보기 컨트롤
	    @FXML
	    private Button btnSalary;
	    
	    @FXML
	 	void handleBtnSalary(ActionEvent event) {
	 		List<Employees> result = service.getOrderByPay();
	 		clear(new ActionEvent());
	 		table.getItems().addAll(result);
	 	}
	    

	    // 3. commission율순으로 직원목록보기
	    @FXML
	    private Button btnCommissionList;

	    @FXML
	    void handleBtnCommissionList(ActionEvent event) {
	    	List<Employees> result = service.getMoreComm(employee_id);
	 		clear(new ActionEvent());
	 		table.getItems().addAll(result);
	    }
	    
	    // 4. 성으로 직원찾기
	    @FXML
	    private TextField txtFirstName;

	    @FXML
	    private Button btnFirstName;

	    @FXML
	    void handleBtnFirstName(ActionEvent event) {
	    	String x = txtFirstName.getText();
	    	List<Employees> result = service.getListByFirstName(x);
	    	clear(new ActionEvent());
	    	table.getItems().addAll(result);
	    }
	    
	    // 5. salary가 10000원 이상인 직원목록
	    @FXML
	    private Button btnSalaryList;
	    
	    @FXML
	    void handleBtnSalaryList(ActionEvent event) {
	    	List<Employees> result = service.moreThanData(employee_id);
			clear(new ActionEvent());
			table.getItems().addAll(result);
	    }
	    
	    // 6. 전화번호로 직원찾기
	    @FXML
	    private TextField txtPhone;
	    
	    @FXML
	    private Button btnPhone;
	    
	    @FXML
	    void handleBtnPhone(ActionEvent event) {
	    	String x = txtPhone.getText();
	    	List<Employees> result = service.getListByPhone(x);
	    	clear(new ActionEvent());
	    	table.getItems().addAll(result);
	    }
	    
	    // 7. 이메일로 직원찾기
	    
	    @FXML
	    private Button btnEmail;
	    
	    @FXML
	    private TextField txtEmailSelect;
	    
	    @FXML
	    void handleBtnEmail(ActionEvent event) {
	    	String x = txtEmailSelect.getText();
	    	List<Employees> result = service.getListByEmail(x);
	    	clear(new ActionEvent());
	    	table.getItems().addAll(result);
	    }
	    
	    // 8. 직업id별 직원목록찾기
	    
	    @FXML
	    private ComboBox<Jobs> comJobId;

	//---------------------------------------
		
		
	@FXML
    private TextField txtEmployee_id;

    @FXML
    private TextField txtFirst_name;

    @FXML
    private TextField txtLast_name;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPhone_number;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextField txtCommission_pct;

    @FXML
    private TextField txtManager_id;

    @FXML
    private TextField txtDepartment_id;

    @FXML
    private ComboBox<Jobs> comJob_id;

    @FXML
    private TextField txtHire_date;

    @FXML
    private DatePicker txtStart_date;

    @FXML
    private DatePicker txtEnd_date;
    
    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnAllData;

    @FXML
    private Button btnIndex;

    @FXML
    private AnchorPane rightPanel;

    @FXML
    private BorderPane txtPane;

	//
	public Date str2Date(String x) {
		String strs[] = x.split("-");
		if(strs[1].charAt(0)=='0') strs[1] = strs[1].replace("0","");
		if(strs[2].charAt(0)=='0') strs[2] = strs[2].replace("0","");
		int year = Integer.parseInt(strs[0]) - 1900;
		int month = Integer.parseInt(strs[1]) - 1;
		int day = Integer.parseInt(strs[2]);
		return new Date(year, month, day);
	}
	Employees currentVo;
	
	private Employees getVo() {
		Employees vo = new Employees();
		vo.setFirst_name(txtFirst_name.getText());
		vo.setLast_name(txtLast_name.getText());
		vo.setEmail(txtEmail.getText());
		vo.setPhone_number(txtPhone_number.getText());
		vo.setHire_date(str2Date(txtHire_date.getText()));
		vo.setJob_id(comJob_id.getSelectionModel().getSelectedItem().getJob_id());
		vo.setSalary(Integer.parseInt(txtSalary.getText()));
		vo.setCommission_pct(Double.parseDouble(txtCommission_pct.getText()));
		vo.setManager_id(Integer.parseInt(txtManager_id.getText()));
		vo.setDepartment_id(Integer.parseInt(txtDepartment_id.getText()));
		return vo;
	}
	
	private Map<String,String> getMap(){
    	Map<String,String>map = new HashMap<>();
    	currentVo.setEmployee_id(getMax()+1);
//    	map.put("EMPLOYEE_ID",currentVo.getEmployee_id()+"");
    	map.put("FIRST_NAME",currentVo.getFirst_name()+"");
    	map.put("LAST_NAME", currentVo.getLast_name()+"");
    	map.put("EMAIL", currentVo.getEmail()+"");
    	map.put("PHONE_NUMBER",currentVo.getPhone_number()+"");
    	map.put("HIRE_DATE",currentVo.getHire_date()+"");
    	map.put("JOB_ID", job_id);
    	map.put("SALARY",currentVo.getSalary()+"");
    	map.put("COMMISSION_PCT",currentVo.getCommission_pct()+"");
    	map.put("MANAGER_ID", currentVo.getManager_id()+"");
    	map.put("DEPARTMENT_ID", currentVo.getDepartment_id()+"");
    	return map;
    }
	
    public int getMax() {
    	int max = 0;
    	try {
			max = employeesdao.getMaxid();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return max;
    }
    
	@FXML
	void handleBtnInsert(ActionEvent event) {

		try {
			edao.insert(getVo());// DB에 반영
			int id = edao.getMaxid();
			System.out.println("id:" + id);
			Employees vo = edao.select(id);
			table.getItems().add(vo); // ui에 반영
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@FXML
    void handleBtnClear(ActionEvent event) {
    	txtEmployee_id.setText(null);
    	txtFirst_name.setText(null);
    	txtLast_name.setText(null);
    	txtEmail.setText(null);
    	txtPhone_number.setText(null);
    	txtHire_date.setText(null);
    	comJob_id.getSelectionModel().clearSelection();
    	txtSalary.setText(null);
    	txtCommission_pct.setText(null);
    	txtManager_id.setText(null);
    	txtDepartment_id.setText(null);
    }

	@FXML
	void handleBtnDelete(ActionEvent event) {
		int selNum = table.getSelectionModel().getSelectedIndex();
		Employees emp = (Employees) table.getSelectionModel().getSelectedItem();
		int empId = emp.getEmployee_id();
		try {
			jhdao.deleteOrphanRecord(empId);
			edao.delete(empId);
			table.getItems().remove(selNum);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void handleBtnAllData(ActionEvent event) {
		try {
			table.getItems().clear();
			table.getItems().addAll(edao.selectAll());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void handleBtnIndex(ActionEvent event) {
		String conditions = JOptionPane.showInputDialog("WHERE 포함한 조건을 입력하세요.");
		try {
			List<Employees> data = edao.selectByConditions(conditions);
			table.getItems().clear();
			table.getItems().addAll(data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void handleBtnUpdate(ActionEvent event) {
		try {
			Employees vo = getVo();
			vo.setEmployee_id(Integer.parseInt(txtEmployee_id.getText()));
			edao.update(vo);// DB에 반영
			int selNum = table.getSelectionModel().getSelectedIndex();
			table.getItems().set(selNum, vo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
    		employeesdao = new EmployeesDAO();
    		jobsdao = new JobsDAO();
//    		dedao = new DepartmentsDAO();
			comJob_id.getItems().addAll(jobsdao.selectAll());
    		table.getItems().addAll(employeesdao.selectAll());
    		setTable();
    		// job
    		comJobId.getItems().addAll(jobsdao.selectAll());
    		comJobId.valueProperty().addListener(new ChangeListener<Jobs>() {
    			@Override
				public void changed(ObservableValue<? extends Jobs> observable, Jobs oldValue,
						Jobs newValue) {
					List<Employees> result = service.getListByJobId(newValue.getJob_id());
					clear(new ActionEvent());
					table.getItems().addAll(result);
				}
    		});	

    	}catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
	}

	public void setTable() throws SQLException {
		table.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				try {
					jobsdao = new JobsDAO();
					Employees selected = (Employees) table.getSelectionModel().getSelectedItem();
					txtEmployee_id.setText(selected.getEmployee_id()+"");
					txtFirst_name.setText(selected.getFirst_name());
					txtLast_name.setText(selected.getLast_name());
					txtEmail.setText(selected.getEmail());
					txtPhone_number.setText(selected.getPhone_number());
					txtHire_date.setText(selected.getHire_date().toString());
					comJob_id.getSelectionModel().select(jobsdao.select(selected.getJob_id()));
					txtSalary.setText(selected.getSalary() + "");
					txtCommission_pct.setText(selected.getCommission_pct() + "");
					txtManager_id.setText(selected.getManager_id()+"");
					txtDepartment_id.setText(selected.getDepartment_id()+ "");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		txtPane.setCenter(table);
	}

	@FXML
	void termHireList(ActionEvent event) {
		Date a = str2Date(txtStart.getText());
		Date b = str2Date(txtEnd.getText());
		List<Employees> result = service.getEmpListByDate(a, b);
		clear(new ActionEvent());
		table.getItems().addAll(result);

	}

}
