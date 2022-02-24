<<<<<<< HEAD
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020-10-05
  Time: 4:42 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chat Room</title>
</head>
<body>
    <h1>Welcome to Concordia Chat Room</h1>
    <form action="ChatServlet" method="POST">
        <textarea  cols="100" rows="10" name="showTextArea"><%=request.getAttribute("inputText")%></textarea>
        <br>
        <label>User name:</label>
        <input type="text" name="username" size="10">
        <br>
        <textarea  cols="80" rows="5"  name="inputText"></textarea>
        <br>
        <button type="submit" name="send" style="width: 100px; height: 40px;font-size: 25px;">Send</button><br>
    </form>

    <form action="ChatServlet" method="GET">
        <label> Download Chat file
            <select name = "format">
                <option value = "txt">TXT</option>
                <option value = "xml">XML</option>
            </select>
        </label>
        <button type="button" name="download">Download Chat File</button>
    </form>
</body>
</html>
=======
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020-10-05
  Time: 4:42 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chat Room</title>
</head>
<body>
    <h1>Welcome to Concordia Chat Room</h1>
    <form action="ChatServlet" method="POST">
        <textarea  cols="100" rows="10" name="showTextArea"><%=request.getAttribute("inputText")%></textarea>
        <br>
        <label>User name:</label>
        <input type="text" name="username" size="10">
        <br>
        <textarea  cols="80" rows="5"  name="inputText"></textarea>
        <br>
        <button type="submit" name="send" style="width: 100px; height: 40px;font-size: 25px;">Send</button><br>
    </form>

    <form action="ChatServlet" method="GET">
        <label> Download Chat file
            <select name = "format">
                <option value = "txt">TXT</option>
                <option value = "xml">XML</option>
            </select>
        </label>
        <button type="button" name="download">Download Chat File</button>
    </form>
</body>
</html>
>>>>>>> c7c4cf8794e8e6d3a7514490f2bb8132db161006
