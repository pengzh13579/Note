[TOC]
## 1 配置
- 安装
`npm install vue-router  --save`
`cnpm install vue-router  --save`
- 引入
```
import VueRouter from 'vue-router'
Vue.use(VueRouter)
```
- 定义路由
```
const routes = [
    { path: '/foo', component: Foo },
    { path: '/bar', component: Bar },
    { path: '*', redirect: '/home' }   /*默认跳转路由*/
]
```
- 实例化路由
```
const router = new VueRouter({
	routes // （缩写）相当于 routes: routes
})
```
- 挂载
```
new Vue({
    el: '#app',
    router，
    render: h => h(App)
});
```
- 根组件模板配置
`<router-view></router-view>`
- 路由跳转
```
<router-link to="/foo">Go to Foo</router-link>
<router-link to="/bar">Go to Bar</router-link>
```
## 2 动态路由传值
- 定义路由
`{ path: '/content/:aid', component: Content }`
- 传值一
`<router-link :to="'/content/'+item.aid">{{item.title}}</router-link>`
- 传值二
`<router-link :to="'/content?aid='+item.aid">{{item.title}}</router-link>`
- 获取一
`var aid = this.$route.params.aid;`
- 获取二
`var aid = this.$route.params.query;`
## 3 路由模式
默认为hash模式，可修改为history模式。
```
const router = new VueRouter({
  mode: 'history',   /*hash模式改为history*/
  routes // （缩写）相当于 routes: routes
})
```
- 区别
   - hash —— 即地址栏 URL 中的 # 符号（此 hash 不是密码学里的散列运算）。比如这个 URL：http://www.abc.com/#/hello，hash 的值为 #/hello。它的特点在于：hash 虽然出现在 URL 中，但不会被包括在 HTTP 请求中，对后端完全没有影响，因此改变 hash 不会重新加载页面。
   - history —— 利用了 HTML5 History Interface 中新增的 pushState() 和 replaceState() 方法。（需要特定浏览器支持）这两个方法应用于浏览器的历史记录栈，在当前已有的 back、forward、go 的基础之上，它们提供了对历史记录进行修改的功能。只是当它们执行修改时，虽然改变了当前的 URL，但浏览器不会立即向后端发送请求。history在使用时，后端服务器需要结合配置。
   - 因此可以说，hash 模式和 history 模式都属于浏览器自身的特性，Vue-Router 只是利用了这两个特性（通过调用浏览器提供的接口）来实现前端路由。一般场景下，hash 和 history 都可以，除非你更在意颜值，# 符号夹杂在 URL 里看起来确实有些不太美丽。
- 总结
   - hash 模式下，仅 hash 符号之前的内容会被包含在请求中，如 http://www.abc.com，因此对于后端来说，即使没有做到对路由的全覆盖，也不会返回 404 错误。
   - history 模式下，前端的 URL 必须和实际向后端发起请求的 URL 一致，如 http://www.abc.com/book/id。如果后端缺少对 /book/id 的路由处理，将返回 404 错误。Vue-Router 官网里如此描述：“不过这种模式要玩好，还需要后台配置支持……所以呢，你要在服务端增加一个覆盖所有情况的候选资源：如果 URL 匹配不到任何静态资源，则应该返回同一个 index.html 页面，这个页面就是你 app 依赖的页面。”
   - 结合自身例子，对于一般的 Vue + Vue-Router + Webpack + XXX 形式的 Web 开发场景，用 history 模式即可，只需在后端（Apache 或 Nginx）进行简单的路由配置，同时搭配前端路由的 404 页面支持。
## 4 路由嵌套
