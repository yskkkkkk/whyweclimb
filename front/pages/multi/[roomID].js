import { useEffect, useState } from "react";
import { useRouter } from "next/router"
import style from '../../styles/waitRoom.module.css';
import axios from 'axios'
import Link from 'next/link';
import SockJS from 'sockjs-client';
import dynamic from "next/dynamic";
import { Suspense } from "react";
// import Engine from '../../components/multiEngine'
const StompJS = require('@stomp/stompjs');
const Engine = dynamic(() => { return import('../../components/multiEngine')}, {ssr:false});

const basicURL = 'https://k6a401.p.ssafy.io/api';
// const basicURL = `http://localhost:8081/api`
const Stomp = StompJS.Stomp;
const stomp = Stomp.over(function(){
  return new SockJS(`${basicURL}/ws-stomp`);
})
stomp.reconnect_delay = 5000;

export default function WaitRoom() {
  const [isStart, setIsStart] = useState(false);
  const router = useRouter();
  const {roomID} = router.query;
  const [userInfo, setUserInfo] = useState();
  const [roomInfo, setRoomInfo] = useState();
  const [groupInfo, setGroupInfo] = useState();

  // function sendMessage(msg){
  //   console.log('hii');
  //   stomp.send(`/pub/chat/message`, {}, JSON.stringify({type:'MOVE', roomId:roomID, sender:'noman1', message:msg}));
  // }

  function startGame(){
    setIsStart(prev=>!prev);
  }

  function receiveMessage(msg){
    console.log('msg',msg)
    setGroupInfo(msg.data);
  }


  function socketConnect(data){
    stomp.connect({},
      function(){
        console.log('stomp',stomp.webSocket._transport.url);
        const strings = stomp.webSocket._transport.url.split('/');
        const sessionId = strings[strings.length-2];
        stomp.subscribe(`/sub/chat/room/`+roomID, function(message){
            console.log('here !!!message',message);
            var recv = JSON.parse(message.body);
            receiveMessage(recv);
        });
        stomp.send(`/pub/room/entrance`,{},JSON.stringify({roomCode:roomID, sessionId:sessionId, userSeq:data.userSeq, userId:data.userId}));
      },
      function(error){
        console.log('error',error.headers.message);
      }
    )
  }

  function getUserInfo(){
    const token = localStorage.getItem("token");
    const headers = {
      'Authorization': token,
      mode: 'no-cors'
    }
    fetch(`https://k6a401.p.ssafy.io/api/user/information`, {headers:headers})
      .then(res => res.json())
      .then(data => {socketConnect(data);setUserInfo(data)})
      .catch(err => console.log(err))
  }
  
  useEffect(()=>{
    if(roomID){
      axios.get(`${basicURL}/chat/room/${roomID}`)
        .then(res=>res.data)
        .then(data=>{
          if(data!==''){
            console.log(data);
            setRoomInfo(data);
            getUserInfo();
          } else {
            location.href="/multi";
          }
        })
    }
  },roomID)

  return (
    <>
      {!isStart && <main className={style.container}>
          <header>welcome to room: {roomID}</header>
          <section>
            {groupInfo && groupInfo.length} / {roomInfo && roomInfo.roomMaxNum}
          </section>
          <section>
            {groupInfo && groupInfo.map(player => <div key={player.userSeq}>{player.userId}</div>)}
          </section>
          <button onClick={startGame}>Start Game</button>
          <Link href={'/multi'}>
            <button>back to Lobby</button>
          </Link>
      </main> }      
      {isStart && <main className={style.container}>
        <div className={style.head}>
          <p className={style.title}>Why We Climb</p>
          
            <p id="mute">Mute<input type="checkbox" /></p>
            <p className={style.time} id="time"></p>
          
        </div>
        <Engine stomp={stomp} roomId={roomID} userInfo={userInfo} groupInfo={groupInfo}/>
        <div className={style.buttons}>
          <Link href={'/'} passHref>
            <a><h3>Back</h3></a>
          </Link>
        </div>
      </main>}
    </>
    
  )
}