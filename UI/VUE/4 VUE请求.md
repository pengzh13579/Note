[TOC]
## 1 使用vue-resource请求数据的步骤
1、安装vue-resource模块，  注意加上  --save
  npm install vue-resource --save /  cnpm install vue-resource --save  
2、引入 vue-resource
  import VueResource from 'vue-resource';
3、main.js  
  Vue.use(VueResource);
4、js
```
this.$http.get(地址).then(function(){  })
```
## 2 使用axios请求数据的步骤
1、安装axios模块，  注意加上  --save  
  cnpm  install  axios --save
2、引入 axios
  import Axios from 'axios';
3、js 
```
  var api='http://xxx';
  Axios.get(api).then((response)=>{
    this.list=response.data.result;
  }).catch((error)=>{
    console.log(error);
  });
```