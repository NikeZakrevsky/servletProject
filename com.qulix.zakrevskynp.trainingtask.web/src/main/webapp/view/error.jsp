<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="style.css" />
    <title>Error</title>
</head>
<body>
<div class="container">
    <div class="row">
    <div class="error-template">
	    <h1>Oops!</h1>
	    <h2>404 Not Found</h2>
	    <div class="error-details">
		Sorry, an error has occured, Requested page not found!<br>
		<?php echo CHtml::encode($message); ?>
	    </div>
	    <div class="error-actions">
		<a href="projectsList" class="btn btn-primary">
		    <i class="icon-home icon-white"></i> Take Me Home </a>
	</div>
    </div>
	</div>
</div>
</body>
</html>
