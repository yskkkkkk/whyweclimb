import { useState, useRef, useEffect } from 'react';
import BasicButton from '../ui/basicButton';
import style from './login.module.css';

export default function Login({toSignup, toModeSelect}) {
  
  const inputID = useRef();
  const pw = useRef();

  const [userID, setUserID] = useState("");
  const [userPassword, setUserPassword] = useState("");

  const initializeData = () => {        // 로그인 입력 정보의 상태들 초기화
    inputID.current.value = '';
    pw.current.value = '';
    setUserID("");
    setUserPassword("");
  };

  const toSignUp = () => {
    initializeData();
    toSignup();
  }

  const handleLoginSubmit = () => {     // 로그인 버튼 누를시 post요청 (백에서 실패사유 알려주면 땡큐)
    fetch('https://k6a401.p.ssafy.io/api/user/login', {
      method: "POST",
      body: JSON.stringify({
        userId: userID,
        userPassword: userPassword,
      }),
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
    })
    .then((response) => {
      return response.json();
    })
    .then((data) => {
      console.log(data);
      if (data.token) {
        localStorage.setItem("token", data.token);
        initializeData();
        // alert(`로그인 성공 : ${data}`)
        toModeSelect();
      }
      else {
        alert('유요하지 않은 아이디 혹은 비밀번호 입니다.');
      }
    })
    .catch((error) => {
      alert(`로그인 실패 : ${error}`);
    });
  };

  useEffect(() => {
    inputID.current.focus();
  }, [])

  return (
    <main className={style.container}>
      <h1>why we climb</h1>
      <div className={style.loginContainer}> 
        <section className={style.login}>
          <h2 className={style.title}>Login</h2>
          <div className={style.card}>
            <label>Id 
              <input type="text" onChange={e => setUserID(e.target.value)} ref={inputID} required />
            </label>
          </div>
          <div className={style.card}>
            <label>Password 
              <input type="password" onChange={e => setUserPassword(e.target.value)} ref={pw} required />
            </label>
          </div>
          <div className={style.btnGroup}>
            <button className={style.loginBtn} onClick={handleLoginSubmit} >Login</button>
            <div className={style.signBtnGroup}>
              <div className={style.btns}>no account?</div>
              <button className={style.signupBtn} onClick={toSignUp} >signup</button>
            </div>
          </div>
        </section>
      </div>
    </main>
  )
}