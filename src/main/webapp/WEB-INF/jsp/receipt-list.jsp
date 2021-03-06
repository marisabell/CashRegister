<%--@elvariable id="receipts" type="java.util.List<ua.kapitonenko.app.domain.Receipt>"--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="ua.kapitonenko.app.config.keys.Keys" %>
<!DOCTYPE html>
<html>

<%@ include file="includes/inner-header.jsp" %>
<body>
<%@ include file="includes/alert.jsp" %>
<%@ include file="includes/logout.jsp" %>
<%@ taglib prefix="u" uri="/WEB-INF/access.tld" %>

<main role="main">
    <div class="container">
        <div class="card">
            <div class="card-body">
                <div class="row">
                    <div class="col-8">
                        <h4 class="card-title mb-3"><fmt:message key="${Keys.RECEIPT_LIST}" bundle="${msg}"/></h4>
                    </div>
                    <u:can action="/reports">
                        <div class="col-2 pl-0 ml-auto mb-4">
                            <a class="btn btn-outline-secondary btn-block" href="/reports" role="button">
                                <fmt:message key="${Keys.REPORT_LIST}" bundle="${msg}"/></a>
                        </div>
                    </u:can>
                    <u:can action="/products">
                        <div class="col-2 pl-0 ml-auto mb-4">
                            <a class="btn btn-outline-secondary btn-block" href="/products" role="button">
                                <fmt:message key="${Keys.PRODUCT_LIST}" bundle="${msg}"/></a>
                        </div>
                    </u:can>
                    <u:can action="/create-receipt">
                        <div class="col-2 pl-0 ml-auto mb-4">
                            <form class="form" method="POST" action="/create-receipt">
                                <button type="submit" class="btn btn-primary btn-block">
                                    <fmt:message key="${Keys.CREATE}" bundle="${msg}"/>
                                </button>
                            </form>
                        </div>
                    </u:can>
                </div>
                <c:if test="${not empty receipts}">
                    <div class="card">
                        <div class="table-responsive">
                            <form class="form" method="POST" autocomplete="off" id="receipt-list-form">
                                <input type="hidden" name="id" id="rId">
                                <input type="hidden" name="action" id="actionId">

                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th scope="col" width="50px"><fmt:message key="${Keys.ID}"
                                                                                  bundle="${msg}"/></th>

                                        <th scope="col" width="100px"><fmt:message
                                                key="${Keys.RECEIPT_TYPE}" bundle="${msg}"/></th>

                                        <th scope="col" width="50px"><fmt:message
                                                key="${Keys.CASHBOX}" bundle="${msg}"/></th>

                                        <th scope="col" width="100px"><fmt:message
                                                key="${Keys.PAYMENT}" bundle="${msg}"/></th>

                                        <th scope="col" width="50px"><fmt:message
                                                key="${Keys.NO_ARTICLES}" bundle="${msg}"/></th>


                                        <th scope="col" width="100px" class="text-right"><fmt:message
                                                key="${Keys.TAX_AMOUNT}" bundle="${msg}"/></th>

                                        <th scope="col" width="100px" class="text-right"><fmt:message
                                                key="${Keys.RECEIPT_TOTAL}" bundle="${msg}"/></th>

                                        <th scope="col" width="100px"><fmt:message
                                                key="${Keys.CREATED_AT}" bundle="${msg}"/></th>

                                        <th scope="col" width="50px"><fmt:message
                                                key="${Keys.STATUS}" bundle="${msg}"/></th>

                                        <th scope="col" width="80px" class="text-center d-print-none"><fmt:message
                                                key="${Keys.ACTION}"
                                                bundle="${msg}"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <c:forEach var="receipt" items="${receipts}">
                                        <tr>
                                            <fmt:setBundle basename="${receipt.receiptType.bundleName}"
                                                           var="recTypeBundle"/>
                                            <fmt:setBundle basename="${receipt.paymentType.bundleName}"
                                                           var="payTypeBundle"/>

                                            <th scope="row">${receipt.id}</th>
                                            <td><fmt:message key="${receipt.receiptType.bundleKey}"
                                                             bundle="${recTypeBundle}"/></td>

                                            <td class="text-centert">${receipt.cashbox.id}</td>
                                            <td><fmt:message key="${receipt.paymentType.bundleKey}"
                                                             bundle="${payTypeBundle}"/></td>

                                            <td class="text-centert">${receipt.products.size()}</td>
                                            <td class="text-right"><fmt:formatNumber type="number" groupingUsed="false"
                                                                                     maxFractionDigits="2"
                                                                                     minFractionDigits="2"
                                                                                     value="${receipt.taxAmount}"/></td>
                                            <td class="text-right"><fmt:formatNumber type="number" groupingUsed="false"
                                                                                     maxFractionDigits="2"
                                                                                     minFractionDigits="2"
                                                                                     value="${receipt.totalCost}"/></td>
                                            <td><fmt:formatDate type="both"
                                                                value="${receipt.createdAt}"/></td>
                                            <td>
                                                <c:if test="${receipt.cancelled}">
                                                    <fmt:message key="${Keys.CANCELLED}" bundle="${msg}"/>
                                                </c:if>
                                            </td>

                                            <td class="text-center">
                                                <c:if test="${!receipt.cancelled}">
                                                    <c:if test="${receipt.returnAllowed}">
                                                        <u:can action="/return-receipt">
                                                            <button type="button" class="btn btn-link"
                                                                    onclick="var form = $('#receipt-list-form');
                                                                            $('#rId').val(${receipt.id});
                                                                            form.attr('action', '/return-receipt');
                                                                            form.submit();">
                                                                <fmt:message key="${Keys.RETURN}" bundle="${msg}"/>
                                                            </button>
                                                        </u:can>
                                                    </c:if>
                                                    <u:can action="/cancel-receipt">
                                                        <button type="button" class="btn btn-link"
                                                                onclick="var form = $('#receipt-list-form');
                                                                        $('#rId').val(${receipt.id});
                                                                        form.attr('action', '/cancel-receipt');
                                                                        form.submit();">
                                                            <fmt:message key="${Keys.CANCEL}" bundle="${msg}"/>
                                                        </button>
                                                    </u:can>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </form>
                        </div>
                    </div>
                    <u:can action="/create-report">
                        <%@ include file="includes/pager.jsp" %>
                    </u:can>
                </c:if>
            </div>
        </div>
    </div>
</main>
<%@ include file="includes/footer.jsp" %>
</body>
</html>
