import React, { Component } from 'react';
import{
  AppRegistry,
  View,
  Text,
  DeviceEventEmitter,
} from 'react-native';

class startComponent extends Component{
   constructor(props){
   super(props);
   this.state = {
   content:null,showModule:false
   };
   DeviceEventEmitter.addListener("test", (result) => {
      let mainComponent = require(result.name);
      this.setState({
      content:mainComponent,
      showModule:true
      })
   });
   }
   render(){
      let _content = null;
      if(this.state.content){
       _content = React.createElement(this.state.content,this.props);
       return _content;
      }else{
      return (<Text>dadsassssssssssssssssssssssssssss</Text>)
      }
   }
}
AppRegistry.registerComponent('mainRNModule', () => startComponent);