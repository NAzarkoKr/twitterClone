<#import "parts/common.ftl" as c>

<@c.page>
<div class="form-row">
    <div class="form-group col-md-6">
        <form method="post" action="filter" class="form-inline">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input class="form-control" type="text" name="filter" value="${filter?ifExists}" placeholder="Search">
            <button type="submit" class="btn btn-primary ml-2">Search</button>
        </form>
    </div>
</div>

<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
Add new Message
</a>
<div class="collapse" id="collapseExample" >
      <div class="form-group mt-3">

          <form method="post" enctype="multipart/form-data">
              <div class="form-group">
                  <input class="form-control" type="text" name="text" placeholder="Put massege"/>
              </div>
              <div class="form-group">
                  <input class="form-control" type="text" name="tag" placeholder="Tag">
              </div>
              <div class="form-group">
                  <div class="custom-file">
                      <input type="file" name="file" class="custom-file-input" id="customFile">
                      <label class="custom-file-label" for="customFile">Choose file</label>
                  </div>
              </div>

              <input type="hidden" name="_csrf" value="${_csrf.token}">

              <div class="form-group">
                  <button type="submit" class="btn btn-primary">Add</button>
              </div>
          </form>
      </div>
</div>

<div class="card-columns">

    <#list messages as message>
        <div class="card my-3" style="width: 18rem; ">

                <#if message.filename??>
                    <img src="/images/${message.filename}" class="card-img-top">
                </#if>
        <div class="m-2">
            <span>${message.text}</span>
            <i>${message.tag}</i>
        </div>
            <div class="card-footer text-multed">
                ${message.authorName}
            </div>
        </div>
    </#list>
</div>
</@c.page>
