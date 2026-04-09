import { useState } from 'react';
import style from './joinModal.module.css';
import axios from 'axios'
import toast from 'react-hot-toast';
import { motion } from 'framer-motion';

export default function JoinModal({handleClose}) {
  const basicURL = process.env.NEXT_PUBLIC_API_URL
  const [isInterference, setIsInterference] = useState("false");

  // const handleChange = (e) => {
  //   setIsInterference(e.target.value);
  // }

  const joinRoom = () => {
    axios.get(`${basicURL}/room?roomInterference=${isInterference}`, { withCredentials: true })
      .then(res => res.data)
      .then(data => {
        if(data.length === 0) {
          toast("There's no available room. \nPlease try again later.", {icon: "😢"});
        } else {
          location.href=`multi/${data.roomCode}`;
        }
      })
      .catch(e => {
        console.error(e);
        toast.error("unexpected error occured.\nPlease try again later.");
      })
  }

  const popUp = {
    initial: {
      y: "-30vh",
      opacity: 0,
    },
    visible: {
      y: "0",
      opacity: 1,
      transition: {
        duration: 0.1,
        type: "spring",
        damping: 25,
        stiffness: 500,
      }
    },
    exit: {
      y: "-30vh",
      opacity: 0,
      transition: {
        duration: 0.2,
      }
    }
  }

  return (
    <motion.div
      onClick={(e) => e.stopPropagation()}
      variants={popUp}
      initial="initial"
      animate="visible"
      exit="exit"
    >
      <section className={style.modal}>
        <h2>join room</h2>        
        <div className={style.btns}>
          <button className={style.okBtn} onClick={joinRoom} >join</button>
          <button className={style.backBtn} onClick={handleClose} >close</button>
        </div>
      </section>
    </motion.div>
  )
}