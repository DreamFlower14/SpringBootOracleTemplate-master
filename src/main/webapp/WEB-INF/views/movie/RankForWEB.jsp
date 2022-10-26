<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // controller로부터 전달받은 데이터
    String res = CmmUtil.nvl((String) request.getAttribute("res"), "0");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>CGV 영화 수집 결과</title>
</head>
<body>
CGV 영화 홈페이지에서 <%=res%>개의 영화 순위 정보가 수집되었습니다.
</body>
</html>