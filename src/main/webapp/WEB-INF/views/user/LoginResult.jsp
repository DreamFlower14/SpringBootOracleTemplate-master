<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%
    // Controller 에 저장된 세션으로 로그인 할 때 생성됨
    String SS_USER_ID = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));

    String res = CmmUtil.nvl((String) request.getAttribute("res"));

    String msg = "";

    if (res.equals("1")){
        msg = SS_USER_ID + "님이 로그인 되었습니다,";
    }else if (res.equals("0")){
        msg = "아이디 비밀번호가 일치하지 않습니다.";
    }else {
        msg = "시스템에 문제가 발생했습니다. 잠시후 다시 시도하여 주시길 바랍니다.";
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title><%=msg%></title>
</head>
<body>
<%=msg%>
</body>
</html>