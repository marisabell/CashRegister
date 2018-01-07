<jsp:useBean id="newProduct" scope="request" class="ua.kapitonenko.app.domain.records.Product"/>
<%--@elvariable id="rcalculator" type="ua.kapitonenko.app.domain.Receipt"--%>
<%--@elvariable id="product" type="ua.kapitonenko.app.domain.records.Product"--%>
<%--@elvariable id="products" type="java.util.List<ua.kapitonenko.app.domain.records.Product>"--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="ua.kapitonenko.app.config.keys.Keys" %>
<!DOCTYPE html>
<html>

<%@ include file="includes/inner-header.jsp" %>
<body>
<%@ include file="includes/alert.jsp" %>
<%@ include file="includes/logout.jsp" %>

<main role="main">
    <div class="container">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title mb-4">
                    <fmt:message key="${rcalculator.receipt.receiptType.bundleKey}" bundle="${settings}"/>&nbsp;
                    <fmt:message key="${Keys.ID}" bundle="${msg}"/>${rcalculator.receipt.id}
                </h5>
                <form class="form" method="POST" action="${action}" autocomplete="off" id="receipt-form">
                    <input type="hidden" name="${Keys.PRODUCT_ID}" id="productId">
                    <input type="hidden" name="button" id="buttonId">
                    <div class="row mb-4">
                        <div class="col-2">
                            <label><fmt:message key="${Keys.CASHBOX}" bundle="${msg}"/>:</label>
                            <b>${rcalculator.receipt.cashboxId}</b>
                        </div>
                        <div class="col-3">
                            <label><fmt:message key="${Keys.CASHBOX_FN}" bundle="${msg}"/>:</label>
                            <b>${rcalculator.receipt.cashbox.fnNumber}</b>
                        </div>
                        <div class="col-4">
                            <label><fmt:message key="${Keys.CASHBOX_ZN}" bundle="${msg}"/>:</label>
                            <b>${rcalculator.receipt.cashbox.znNumber}</b>
                        </div>
                        <div class="col-3 text-right">
                            <fmt:formatDate type="both" value="${rcalculator.receipt.createdAt}"/>

                        </div>
                    </div>
                    <c:if test="${not empty rcalculator.products}">
                        <div class="card mb-4 product-list">
                            <div class="table-responsive">

                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th scope="col" width="50px"><fmt:message key="${Keys.ID}"
                                                                                  bundle="${msg}"/></th>

                                        <th scope="col" width="250px"><fmt:message key="${Keys.PRODUCT}"
                                                                                   bundle="${msg}"/></th>

                                        <th scope="col" width="140px" class="text-center"><fmt:message
                                                key="${Keys.PRODUCT_QUANTITY}" bundle="${msg}"/></th>

                                        <th scope="col" width="80px" class="text-center"><fmt:message
                                                key="${Keys.PRODUCT_UNIT}"
                                                bundle="${msg}"/></th>

                                        <th scope="col" width="100px" class="text-center"><fmt:message
                                                key="${Keys.PRODUCT_TAX}" bundle="${msg}"/></th>

                                        <th scope="col" width="100px" class="text-right"><fmt:message
                                                key="${Keys.PRODUCT_PRICE}" bundle="${msg}"/></th>

                                        <th scope="col" width="100px" class="text-right"><fmt:message
                                                key="${Keys.RECEIPT_TOTAL}" bundle="${msg}"/></th>

                                        <th scope="col" width="100px" class="text-center d-print-none"><fmt:message
                                                key="${Keys.ACTION}"
                                                bundle="${msg}"/></th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <c:forEach var="product" items="${rcalculator.products}">
                                        <tr>
                                            <th scope="row">${product.id}</th>

                                            <td>${product.name}</td>

                                            <td>
                                                <div class="input-group input-group-sm">
                                                    <input type="text" class="form-control text-right"
                                                           aria-describedby="add-${product.id}"
                                                           name="${Keys.PRODUCT_QUANTITY}"
                                                           value="<fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="3"
                                                                                     minFractionDigits="3" value="${product.quantity}" />">
                                                    <div class="input-group-append d-print-none">
                                                        <button class="btn btn-secondary" type="submit"
                                                                name="button" value="button.update">
                                                            <i class="fa fa-check" aria-hidden="true"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </td>
                                            <td class="text-center"><fmt:message key="${product.unit.bundleKey}"
                                                                                 bundle="${settings}"/></td>

                                            <td class="text-center">
                                                <fmt:message key="${product.taxCategory.bundleKey}"
                                                             bundle="${settings}"/>
                                            </td>

                                            <td class="text-right">
                                                <fmt:formatNumber type="number" groupingUsed="false"
                                                                  maxFractionDigits="2"
                                                                  minFractionDigits="2" value="${product.price}"/></td>

                                            <td class="text-right">
                                                <fmt:formatNumber type="number" groupingUsed="false"
                                                                  maxFractionDigits="2"
                                                                  minFractionDigits="2" value="${product.cost}"/></td>

                                            <td class="text-center d-print-none">
                                                <button type="button" class="btn btn-link"
                                                        onclick="
                                                                $('#buttonId').val('button.delete');
                                                                $('#productId').val(${product.id});
                                                                $('#receipt-form').submit();
                                                                ">

                                                    <fmt:message key="${Keys.DELETE}"
                                                                 bundle="${msg}"/>
                                                </button>
                                            </td>

                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="card-footer">
                                <dl class="row" id="total">
                                    <c:forEach var="taxCatEntry" items="${rcalculator.taxByCategory.entrySet()}">
                                        <dt class="col-2 offset-8 col-md-2 offset-md-8">
                                            <fmt:message key="${taxCatEntry.key.bundleKey}" bundle="${settings}"/></dt>
                                        <dd class="col-2 col-md-2">${taxCatEntry.value}</dd>
                                    </c:forEach>

                                    <dt class="col-2 offset-8 col-md-2 offset-md-8">
                                        <fmt:message key="${Keys.TAX_AMOUNT}" bundle="${msg}"/></dt>
                                    <dd class="col-2 col-md-2">${rcalculator.taxAmount}</dd>

                                    <dt class="col-2 offset-8 col-md-2 offset-md-8 total">
                                        <fmt:message key="${Keys.RECEIPT_TOTAL}" bundle="${msg}"/></dt>
                                    <dd class="col-2 col-md-2 total">${rcalculator.totalCost}</dd>
                                </dl>
                            </div>
                        </div>
                    </c:if>
                    <div class="form-row mb-4 d-print-none">
                        <div class="col-1">
                            <label><fmt:message key="${Keys.PRODUCT}" bundle="${msg}"/></label>
                            <input type="text" class="form-control"
                                   placeholder="<fmt:message key="${Keys.ID}" bundle="${msg}"/>"
                                   name="${Keys.NEW_PRODUCT_ID}" value="${newProduct.id}">
                        </div>
                        <div class="col-6">
                            <label>&nbsp;</label>
                            <input type="text" class="form-control"
                                   placeholder="<fmt:message key="${Keys.PRODUCT_NAME}" bundle="${msg}"/>"
                                   name="${Keys.NEW_PRODUCT_NAME}" value="${newProduct.name}">
                        </div>
                        <div class="col-3 col-sm-3">
                            <label><fmt:message key="${Keys.PRODUCT_QUANTITY}" bundle="${msg}"/></label>
                            <input type="text" class="form-control"
                                   name="${Keys.NEW_PRODUCT_QUANTITY}" value="${newProduct.quantity}">
                        </div>
                        <div class="col-3 col-sm-2">
                            <label>&nbsp;</label>
                            <button type="submit" class="btn btn-secondary btn-block"
                                    name="button" value="button.add">
                                <fmt:message key="${Keys.ADD}" bundle="${msg}"/></button>
                        </div>
                    </div>

                    <div class="form-group">
                        <label><fmt:message key="${Keys.PAYMENT}" bundle="${msg}"/>
                        </label>
                        <c:forEach items="${paymentTypes}" var="payType">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="payment" id="payment-${payType.id}"
                                       value="${payType.id}" ${rcalculator.receipt.paymentTypeId.equals(payType.id) ? "checked" : ""}>
                                <label class="form-check-label" for="payment-${payType.id}">
                                    <fmt:message key="${payType.bundleKey}" bundle="${settings}"/>
                                </label>
                            </div>
                        </c:forEach>
                    </div>

                    <div class="form-row d-print-none">
                        <div class="col-xs-6 col-md-2">
                            <button type="submit" class="btn btn-primary btn-block"
                                    name="button" value="button.save">
                                <fmt:message key="${Keys.SAVE}" bundle="${msg}"/></button>
                        </div>

                        <div class="col-xs-6 col-md-2">
                            <button type="submit" class="btn btn-secondary btn-block"
                                    name="button" value="button.cancel">
                                <fmt:message key="${Keys.CANCEL}" bundle="${msg}"/></button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</main>
<%@ include file="includes/footer.jsp" %>
</body>
</html>
