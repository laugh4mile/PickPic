<template>
  <div>
    <div class="progress">
      <div
        class="progress-bar"
        role="progressbar"
        aria-valuenow="60"
        aria-valuemin="0"
        aria-valuemax="100"
        style="width: 60%;"
      >
        <span class="sr-only">60% Complete</span>
      </div>
      <span class="progress-type">HTML / HTML5</span>
      <span class="progress-completed">60%</span>
    </div>
    <div class="progress">
      <div
        class="progress-bar progress-bar-success"
        role="progressbar"
        aria-valuenow="40"
        aria-valuemin="0"
        aria-valuemax="100"
        style="width: 40%"
      >
        <span class="sr-only">40% Complete (success)</span>
      </div>
      <span class="progress-type">ASP.Net</span>
      <span class="progress-completed">40%</span>
    </div>
    <div class="progress">
      <div
        class="progress-bar progress-bar-info"
        role="progressbar"
        aria-valuenow="20"
        aria-valuemin="0"
        aria-valuemax="100"
        style="width: 20%"
      >
        <span class="sr-only">20% Complete (info)</span>
      </div>
      <span class="progress-type">Java</span>
      <span class="progress-completed">20%</span>
    </div>
    <div class="progress">
      <div
        class="progress-bar progress-bar-warning"
        role="progressbar"
        aria-valuenow="60"
        aria-valuemin="0"
        aria-valuemax="100"
        style="width: 60%"
      >
        <span class="sr-only">60% Complete (warning)</span>
      </div>
      <span class="progress-type">JavaScript / jQuery</span>
      <span class="progress-completed">60%</span>
    </div>
    <div class="progress">
      <div
        class="progress-bar progress-bar-danger"
        role="progressbar"
        aria-valuenow="80"
        aria-valuemin="0"
        aria-valuemax="100"
        style="width: 80%"
      >
        <span class="sr-only">80% Complete (danger)</span>
      </div>
      <span class="progress-type">CSS / CSS3</span>
      <span class="progress-completed">80%</span>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style>
body {
  margin: 30px 0px;
}
.progress {
  position: relative;
  height: 25px;
}
.progress > .progress-type {
  position: absolute;
  left: 0px;
  font-weight: 800;
  padding: 3px 30px 2px 10px;
  color: rgb(255, 255, 255);
  background-color: rgba(25, 25, 25, 0.2);
}
.progress > .progress-completed {
  position: absolute;
  right: 0px;
  font-weight: 800;
  padding: 3px 10px 2px;
}
</style>
