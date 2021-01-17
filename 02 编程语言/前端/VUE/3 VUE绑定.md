[TOC]
## 1 数据绑定MVVM
双向数据绑定必须在表单里面使用，model改变影响视图view，视图view改变影响model。
```
<input  type="text" v-model="msg" ref="inp"/>

<script>
export default {
  name: 'app',
  data () {
    return {
      msg: 'fsafasad'
       
    }
  },
  methods:{
    getMsg:function(){
      //获取model中的msg值
      alert(this.msg)
    },
    setMsg:function(){
      //设置model重的msg值
      this.msg="fasdf";  
      console.log(this.$refs.inp.value) ;
      //改变view的属性首先获取dom节点，通过view重的ref，然后this.$refs.(ref) 来获取dom节点，然后对dom节点进行赋值操作  
     this.$refs.inp.style.background="red"; 
    }
  }
}
</script>
```
## 2 事件绑定
```
<button @click="getMsg()">获取data里面的msg</button>
<button @click="setMsg()">改变data里面的msg</button>
<button @click="deleteData('111')">执行方法传值111</button>
<button data-aid='123' @click="eventFn($event)">事件对象</button> 
<script>
	export default {     
      data () { 
        return {
          msg: '你好vue',
          list:[]      
        }
      },
      methods:{
      	getMsg(){
            alert(this.msg);
        },
        setMsg(){
          this.msg="我是改变后的数据"
        },
        deleteData(val){
            alert(val);
        },
        eventFn(e){
          console.log(e);
          // e.srcElement  dom节点
          e.srcElement.style.background='red';
          // 获取自定义属性的值
          console.log(e.srcElement.dataset.aid);  
        }
      }
    }
</script>
```


