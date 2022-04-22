import { useRef, useEffect } from 'react';
import style from './login.module.css';

export default function Login({toSignup}) {
  
  const inputID = useRef();

  useEffect(() => {
    inputID.current.focus();
  }, [])

  return (
    <section className={style.login}>
      <div className={style.card}>
        <label>Id <input type="text" ref={inputID} required /></label>
      </div>
      <div className={style.card}>
        <label>Password <input type="password" required /></label>
      </div>
      <section className={style.btns}>
        <button >login</button>
        <span>no account? click 
          <a href='#' onClick={toSignup} className={style.toSignup}> here!</a>
        </span>
      </section>
    </section>
  )
}