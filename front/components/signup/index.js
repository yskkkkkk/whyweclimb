import style from './signup.module.css';
import { useState } from 'react';

const ID_REGEX = /^[a-zA-z][a-zA-Z0-9]{3,20}$/;
const PW_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$*]).{8,24}$/;

export default function Signup ({toLogin}) {
  
  const [userId, setUserId] = useState("");
  const [validUserId, setValidUserId] = useState(false);
  const [availableUserId, setAvailableUserId] = useState(false);

  const [userPassword, setUserPassword] = useState("");
  const [validUserPassword, setValidUserPassword] = useState(false);

  const [matchPassword, setMatchPassword] = useState("");
  const [validMatchPassword, setValidMatchPassword] = useState(false);

  const [formReady, setFormReady] = useState(false);

  
  const initializeData = () => {      // 회원가입 누르면 회원 정보 입력창 정보 초기화
    setUserId("");
    setUserPassword("");
    setMatchPassword("");
    setValidUserId(false);
    setAvailableUserId(false);
    setValidUserPassword(false);
    setValidMatchPassword(false);
    setFormReady(false);
  };

  const userIdCheck = (e) => {         // 중복되는 아이디 없는지 확인
    e.preventDefault();
    if (validUserId && userId) {
      fetch(/**/)
        .then((response) => response.json())
        .then((data) => {
          if (data) {
            setAvailableUserId(data);
            alert('유효한 아이디입니다');
          }
          else {
            alert('이미 사용중인 아이디입니다');
          }
        });
    }
    else {
      alert('유효한 아이디를 입력해주세요');
    }
  }
 
  

  return (
    <main className={style.container}>
      <h2>
        signup
      </h2>
      <section className={style.login}>
        <div className={style.card}>
          <label>Id 
            <input type="text" />
            <button onClick={userIdCheck}>중복확인</button>
          </label>
        </div>
        <div className={style.card}>
          <label>Password <input type="password" /></label>
        </div>
        <div className={style.card}>
          <label>PW confirm <input type="password" /></label>
        </div>
      </section>
      <a href="#" className={style.btns} onClick={toLogin}>back</a>
    </main>
  )

}