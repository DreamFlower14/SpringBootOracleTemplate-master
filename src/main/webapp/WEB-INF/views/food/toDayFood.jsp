<%@ page import="kopo.poly.dto.FoodDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%
    List<FoodDTO> rList = (List<FoodDTO>) request.getAttribute("rList");
    if(rList == null) rList = new ArrayList<>();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>서울 강서 캠퍼스 식단</title>
</head>
<body>
이번주 학교 점심식사<br/>
--------------------------------------------<br/>
<%
    for (FoodDTO rDTO : rList) {
        out.println(CmmUtil.nvl(rDTO.getDay()));
        out.println("메뉴 : " + CmmUtil.nvl(rDTO.getFood_nm()));
        out.println("<hr/>");
    }
%>
</body>
</html>