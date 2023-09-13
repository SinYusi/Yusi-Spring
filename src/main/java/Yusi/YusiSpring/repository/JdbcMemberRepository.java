package Yusi.YusiSpring.repository;

import Yusi.YusiSpring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//여기서 사용되는 스타일은 고전 스타일
public class JdbcMemberRepository implements MemberRepository{

    private final DataSource dataSource; //데이터에 붙으려면 데이터소스가 필요하다.

    public JdbcMemberRepository(DataSource dataSource){
        this.dataSource = dataSource; //스프링으로부터 주입받는다.
        //dataSource.getConnection(); //진짜 연결된 데이터베이스(h2)의 소켓과 연결될 수 있다. 여기에 sql문을 날려서 db에 전달해 주는 것이다.
    }
    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)";
        //sql을 만들어 놓는다. 이건 변수 보다는 상수로 놓는게 좋다.
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //resultset이란 결과를 받는 것이다.
        //여기서 사용된 변수들은 외부로 연결되어 있는 것들이기에 절이 끝나면 바로 끊어줘야한다.
        //이 변수들을 릴리즈 해주지 않으면 진짜 끝장난다.
        try { //exception이 굉장히 많기 때문에 try catch를 굉장히 잘 해 줘야한다.
            conn = getConnection();
            //db와 연결
            pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            //return_generated_keys란 인써트를 해봐야 key값이 1,2가 되는 것을 알 수 있 듯이 이를 확인하기 위한 키워드
            pstmt.setString(1, member.getName());
            //parametindex 1번을 해두면 values(?)의 ?와 매칭이 된다. 여기에 member.getName으로 값을 넣었다.
            pstmt.executeUpdate();
            //db에 실제 쿼리가 날라간다.
            rs = pstmt.getGeneratedKeys();
            //위의 return_generated_keys와 매칭돼서 사용
            //db가 키값이 1번이면 1번을 반환, 2번이면 2번 반환
            if (rs.next()) { //rs 다음의 값이 있다면
                member.setId(rs.getLong(1));
                //값을 꺼낸 후 member에 값을 세팅해준다.
            } else {
                throw new SQLException("id 조회 실패");
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
            //위에서 말했던 변수들을 릴리즈 해주는 것
        }
    }
    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            //조회는 executeQuery
            if(rs.next()) { //값이 있으면
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                //멤버 객체를 만든 후
                return Optional.of(member);
                //반환
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public List<Member> findAll() {
        String sql = "select * from member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Member> members = new ArrayList<>();
            //리스트를 만들어서
            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
                //쭉 돌리면서 멤버 객체를 리스트에 넣어준다.
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
