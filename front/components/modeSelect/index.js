import style from './modeSelect.module.css';
import Link from 'next/link';
import Modal from "../ui/modal/modal";
import React, {useState} from 'react';
import axios from'axios';
export default function ModeSelect({toMain, toConfigure}) {
  
  // const test = (e) => {
  //   e.preventDefault();

  //   const token = sessionStorage.getItem("token");
  //   console.log(token);
  //   const headers = {
  //     'Authorization': token,
  //     mode: 'no-cors'
  //   }
  //   fetch('https://k6a401.p.ssafy.io/api/user/information', {headers: headers})
  //   .then((response) => {
  //     console.log(response.json());
  //   });
  // };
  const [Modalshow, setModalVisible] = useState(false);
  const openModal = () => {
    // console.log("show")
    setModalVisible(true)
  }
  const closeModal = () => {
    setModalVisible(false)
  }
  const saveCharacter = (num) => {
    // console.log("clickCharacter")
    axios({
      url:'https://k6a401.p.ssafy.io/api/user/information',
      method:'GET',
      headers: {
        "Authorization":sessionStorage.getItem("token")
      }
    }).then(res=>{
      axios({
        url:`https://k6a401.p.ssafy.io/api/user`,
        method:'PUT',
        headers: {
          "Authorization": sessionStorage.getItem("token")
        },
        data:{
          "userSeq": res.data.userSeq ,
          "skinSeq": num ,
        }
        
      }).then(res=>{
        
        // console.log(res)
        setModalVisible(false)
      }).catch(err=>console.error(err))
    }).catch(err=>console.error(err))
    
  }
  // const characters = () => {
  //   const result = [];
  //   for (let i = 1; i<=4;i++){
      
  //     result.push(<a onClick={saveCharacter(i)}><img className={style.characters} src = {`/images/${i}/running_R1.png`} /></a>)
  //   }
  //   return result
  // }
  return (
    <main className={style.container}>
      <a onClick={openModal} className={style.btn_select}><h3>Character Select</h3></a>
      <Modal visible={Modalshow} closable={true} maskClosable={true}> 
      <h3>Character Select</h3>
        <div className={style.inModal}>
          <div className={style.modalHalf}>
            <a className={style.imageA}onClick={(e)=>{saveCharacter(1, e)}}><img className={style.characters} src = {`/images/${1}/running_R1.png`} /></a>
            
            <a className={style.imageA}onClick={(e)=>{saveCharacter(2, e)}}><img className={style.characters} src = {`/images/${2}/running_R1.png`} /></a>
            
          </div>
          <div className={style.modalHalf}>
            <a className={style.imageA}onClick={(e)=>{saveCharacter(3, e)}}><img className={style.characters} src = {`/images/${3}/running_R1.png`} /></a>
            
            <a className={style.imageA}onClick={(e)=>{saveCharacter(4, e)}}><img className={style.characters} src = {`/images/${4}/running_R1.png`} /></a>
            
          </div>
        </div>
        <a className={style.closeButton} onClick={closeModal}><h4>Close</h4></a>
      </Modal>
      <section className={style.container2}>
        <a className={style.btn} href={'/single/singleGame'}>
          <div className={style.stage}>
            <img className={`${style.box} ${style.bounce7}`} src="/images/running_R1.png" alt="a character jumping image" />
          </div>
          <h2>Single Mode</h2>
        </a>
        <a className={style.btn1} href={'/multi'}>
          <div className={style.stage1}>
            {/* https://css-tricks.com/making-css-animations-feel-natural/ 참고 */}
            <img className={`${style.box1} ${style.bounce1}`} src="/images/sourceror.png" alt="a character jumping image" />
            <img className={`${style.box1} ${style.bounce2}`} src="/images/dwarf.png" alt="a character jumping image" />
            <img className={`${style.box1} ${style.bounce3}`} src="/images/woodElf.png" alt="a character jumping image" />
          </div>
          <h2 className={style.glow}>Multi Mode</h2>
        </a>
      </section>
      {/* <button onClick={toConfigure}>configure</button> */}
      <button className={style.btnBack} onClick={toMain}>logout</button>
    </main>
  )
}