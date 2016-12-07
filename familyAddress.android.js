import React, { Component } from 'react';
import{
  AppRegistry,
  StyleSheet,
  Text,
  Image,
  ListView,
  View,
  TouchableWithoutFeedback,
  NativeModules
} from 'react-native';

const ds = new ListView.DataSource({rowHasChanged:(r1,r2) =>r1 !== r2});

class FamilyAddressComponent extends Component{
     constructor(props){
       super(props);
       let arrayData = [];
       this.state={
       showNull:2,
       selectedID:0,
       list:arrayData,
       rowData:ds.cloneWithRows(arrayData)
       };
       this.getFamilyAddressList();
      }
     render(){
        let showWhat = this.state.showNull;
        if(showWhat===0){
           return <NoDataView />;
        }else if(showWhat===1){
           return (
           <View>
                 <Text style={{marginTop:15,marginLeft:16,fontSize:14,color:"#6e7080"}}>每日首单以您的家庭住址为基准就近派单</Text>
                 <ListView
                 style={styles.ListViewStyle}
                 dataSource = {this.state.rowData}
                 renderRow = {this._renderRow.bind(this)}
                 />
           </View>
           )
        }else{
           return <View/>
        }
     }
     _renderRow(rowData,sectionID,rowID,highlightRow){
     return (
     <View>
            <TouchableWithoutFeedback
               onPress = {function(){
                      NativeModules.NativeMethod.setDefaultAddress(rowData.addrId,(result)=>{
                        if(this.state.selectedID!==rowID){
                           let arrayData = this.state.list.concat();
                           arrayData[rowID].selected=true;
                           arrayData[this.state.selectedID].selected=false;
                           this.setState({
                           selectedID:rowID,
                           list:arrayData,
                           rowData:ds.cloneWithRows(arrayData)
                           });
                           }
                         NativeModules.NativeMethod.showSuccessToast("默认地址已更新\n新地址即刻生效");
                      },()=>{
                         NativeModules.NativeMethod.showWarnToast("修改失败，请稍后再试");
                      });

                         }.bind(this)

               }>
               <View>
               <ViewItem data = {rowData} id={rowID}/>
               </View>
            </TouchableWithoutFeedback>
      </View>
     );
     }
     getFamilyAddressList(){
     NativeModules.NativeMethod.getAddressList((arrayData)=>{
             if(arrayData.length===0){
             this.setState({
               showNull:0,
               selectedID:0,
               list:[],
               rowData:ds.cloneWithRows([])
             });
             }else{
             let id =0;
             for(var i=0;i<arrayData.length;i++){
                if(arrayData[i].selected===true){
                   id=i;
                   break;
                }
             }
             this.setState({
               showNull:1,
               selectedID:id,
               list:arrayData,
               rowData:ds.cloneWithRows(arrayData)
            });
            }
         }
     ,()=>{
             this.setState({
               showNull:0,
               selectedID:0,
               list:[],
               rowData:ds.cloneWithRows([])
            });
     });
     }
   }
   class ViewItem extends Component{
       render(){
           let viewStyleCenter=styles.ViewItemCenter;
           let textStyle10=styles.TextStyle10;
           let textStyle14=styles.TextStyle14;
           if(this.props.data.selected){
              viewStyleCenter=styles.ViewItemCenterSelect;
              textStyle10=styles.TextStyle10Select;
              textStyle14=styles.TextStyle14Select;
           }
         return(
           <View style={styles.ViewItemStyle}>
              <View style={viewStyleCenter}>
                 <Text style={textStyle14}>{this.props.data.detailAddr}</Text>
                 <View style={styles.ViewImageAndTextStyle}>
                 <Image style = {styles.ImageStyle} source={this.props.data.selected?{uri:'rent_common_radio_btn_select'}:{uri:'rent_common_radio_btn_disable'}}></Image>
                 <Text style={textStyle10}>设置为默认</Text>
                 </View>
              </View>
           </View>
         )
       }
   }
class NoDataView extends Component{
      render(){
         return(
         <View style={{flex:1}}>
            <View style={{flex:1}}>
            </View>
            <View style={{flex:2,alignItems:'center'}}>
                <Text style={{fontSize:15,color:"#6e7080"}}>您还未添加家庭住址，请尽快联系运控进行添加</Text>
                <Text style={{fontSize:13,color:"#fc9153"}}>添加后每日首单将以您的家庭住址为基准就近派单</Text>
            </View>
         </View>
         )
      }
}
   const styles = StyleSheet.create({
      ListViewStyle:{
      marginLeft:10,
      marginRight:10,
      flex:1,
      },
      ViewItemStyle:{
      marginTop:10,
      paddingTop:15,
      paddingBottom:15,
      paddingLeft:16,
      paddingRight:16,
      borderRadius:2,
      backgroundColor:'white',
      borderWidth:0.5,
      borderColor:'#e5e5e5',
      },
      ViewItemCenter:{
      alignItems:'center',
      flexDirection:'row'
      },
      ViewItemCenterSelect:{
      alignItems:'center',
      flexDirection:'row',
      },
      ViewImageAndTextStyle:{
      width:80,
      flexDirection:'column',
      alignItems:'flex-end',
      justifyContent:'center',
      },
      ImageStyle:{
      width:15,
      height:15
      },
      TextStyle10:{
      marginTop:5,
      fontSize:12,
      color:"#999"
      },
      TextStyle10Select:{
      marginTop:5,
      fontSize:12,
      color:"#fc9153"
      },
      TextStyle12:{
      fontSize:14,
      color:"#6e7080"
      },
      TextStyle14:{
      flex:1,
      fontSize:16,
      color:"#6e7080"
      },
      TextStyle12Select:{
      fontSize:14,
      color:"#fc9153"
      },
      TextStyle14Select:{
      flex:1,
      fontSize:16,
      color:"#fc9153"
      }
   });
 module.exports = FamilyAddressComponent;