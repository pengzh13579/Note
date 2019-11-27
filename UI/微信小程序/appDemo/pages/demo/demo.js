// pages/test/test.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    listData:[
      {"code":"01","text":"text1","type":"type1"},
      {"code":"02","text":"text2","type":"type2"},
      {"code":"03","text":"text3","type":"type3"},
      {"code":"04","text":"text4","type":"type4"},
      {"code":"05","text":"text5","type":"type5"},
      {"code":"06","text":"text6","type":"type6"},
      {"code":"07","text":"text7","type":"type7"}
    ],
    scrollLeft: 0, 
    //下拉框
    selectShow: false,//控制下拉列表的显示隐藏，false隐藏、true显示
    selectData: ['15:10', '15:15', '15:20'],//下拉列表的数据
    index: 0,//选择的下拉列表下标
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  /**
   * 点击下拉显示框
   */
  selectTap() {
    this.setData({
      selectShow: !this.data.selectShow
    });
  },
  /**
   * 点击下拉列表
   */
  optionTap(e) {
    let Index = e.currentTarget.dataset.index;//获取点击的下拉列表的下标
    this.setData({
      index: Index,
      selectShow: !this.data.selectShow
    });
  }
})