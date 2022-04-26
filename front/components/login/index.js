import { useState, useRef, useEffect } from 'react';
import style from './login.module.css';

export default function Login({toSignup, toModeSelect}) {
  
  const inputID = useRef();

  const [userID, setUserID] = useState("");
  const [userPassword, setUserPassword] = useState("");

  const initializeData = () => {        // 로그인 입력 정보의 상태들 초기화
    setUserID("");
    setUserPassword("");
  };

  // const handleLoginSubmit = () => {     // 로그인 버튼 누를시 post요청 (백에서 실패사유 알려주면 땡큐)
  //   fetch(/**/, {
  //     method: "POST",
  //     body: JSON.stringify({
  //       userID: userID,
  //       password: userPassword,
  //     }),
  //     headers: {
  //       "Content-Type": "application/json",
  //       Accept: "application/json",
  //     },
  //   })
  //   .then((response) => {
  //     // 참고: https://stackoverflow.com/questions/49725012/handling-response-status-using-fetch-in-react-js/49725163
  //     return response.json();
  //   })
  //   .then((data) => {
  //     localStorage.setItem("token", data.accessToken);
  //     initializeData();
  //     alert(`로그인 성공 : ${data}`)
  //   })
  //   .catch((error) => {
  //     alert(`로그인 실패 : ${error}`);
  //   });
  // };

  useEffect(() => {
    inputID.current.focus();
  }, [])

  return (
    <main className={style.login}>
      <div className={style.card}>
        <label>Id 
          <input type="text" onChange={e => setUserID(e.target.value)} ref={inputID} required />
        </label>
      </div>
      <div className={style.card}>
        <label>Password 
          <input type="password" onChange={e => setUserPassword(e.target.value)} required />
        </label>
      </div>
      <section className={style.btns}>
        <button /*onClick={handleLoginSubmit}*/ onClick={toModeSelect} >login</button>
        <span>no account? click 
          <a href='#' onClick={toSignup} className={style.toSignup}> here!</a>
        </span>
      </section>
    </main>
  )
}