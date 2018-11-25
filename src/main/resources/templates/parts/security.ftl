<#assign
    known = Session.SPRING_SECURITY_CONTEXT??
>
<#if known>
<#--can use all methods User-->
        <#assign
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        name = user.getUsername()
        isAdmin = user.isAdmin()
        >
<#else>
        <#assign
        name = "unknown"
        isAdmin = false
        >

</#if>