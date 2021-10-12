package service;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import java.util.function.Supplier;
import common.ConnectionFactory;
import dao.DepartmentsDAO;
import dao.EmployeesDAO;
import dao.IDao;
import dao.Job_historyDAO;
import dao.JobsDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import vo.Employees;

public class HrService {
	private Connection conn;
	private IDao dao;
	EmployeesDAO emdao;
	DepartmentsDAO dedao;
	Job_historyDAO jhdao;
	JobsDAO jdao;
	
	public HrService() {
		try {
			this.conn=ConnectionFactory.create();
			this.dao=new EmployeesDAO();
			this.emdao = new EmployeesDAO();
			this.dedao = new DepartmentsDAO();
			this.jhdao = new Job_historyDAO();
			this.jdao = new JobsDAO();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//allData얻는 메소드
	public List<Employees> getAllData(){
		List<Employees> data = null;
		try {
			data=dao.selectAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	// 1. 주어진 기간의 입사자 목록찾기
	public List<Employees> getEmpListByDate(Date a,Date b){
		List<Employees> result = null;
		try {
			Predicate<Employees> bt= m->{
				Date x = m.getHire_date();
				return (x.after(a) && x.before(b))
						|| x.equals(a) || x.equals(b);
			};
			result = (List<Employees>) emdao.selectAll().stream().filter(bt).collect(Collectors.toList());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	//// 2. 급여순으로 정렬
	public List<Employees> getOrderByPay(){
		List<Employees> result = null;
		try {
			result = (List<Employees>)emdao.selectAll().stream()
					.sorted((em,em2) -> em2.getSalary() - em.getSalary())
					.collect(Collectors.toList());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	// 3. commission율순으로 직원목록보기
	
	public List<Employees> getMoreComm(double commission_pct){
		return getAllData().stream()
				.filter(h->h.getCommission_pct()>=0.1)
				.collect(Collectors.toList());
	}
	
	//4. 성으로 직원찾기
	public List<Employees> getListByFirstName(String x){
		List<Employees> result = null;
		try {
			Predicate<Employees> btt = m->{
				String a = m.getFirst_name();
				return (a.equals(x));
			};
			result = (List<Employees>) emdao.selectAll().stream()
					.filter(btt).collect(Collectors.toList());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	// 5. salary가 10000원 이상인 직원목록
	public List<Employees> moreThanData(int salary){
		return getAllData().stream()
				.filter(h->h.getSalary()>=10000)
				.collect(Collectors.toList());
	}
	
	//6. 전화번호로 직원 찾기
	public List<Employees> getListByPhone(String x){
		List<Employees> result = null;
		try {
			Predicate<Employees> btt = m->{
				String a = m.getPhone_number();
				return (a.equals(x));
			};
			result = (List<Employees>) emdao.selectAll().stream()
					.filter(btt).collect(Collectors.toList());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	// 7. 이메일로 직원찾기
	public List<Employees> getListByEmail(String x){
		List<Employees> result = null;
		try {
			Predicate<Employees> btt = m->{
				String a = m.getEmail();
				return (a.equalsIgnoreCase(x));
			};
			result = (List<Employees>) emdao.selectAll().stream()
					.filter(btt).collect(Collectors.toList());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	// 8. 직업id별 직원목록
		public List<Employees> getListByJobId(String a){
			List<Employees> result = null;
			
			try {
				Predicate<Employees> btt = m->{
					return m.getJob_id().equals(a);
				};
				result = (List<Employees>) emdao.selectAll()
						.stream().filter(btt).collect(Collectors.toList());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			return result;
		}

	public static void main(String[] args) {
		new HrService().test();
	}
	
	public void test() {
			
			System.out.println(getOrderByPay());
	}
}
