import style from './home.module.css';


export default function Login() {
  
  return (
    <section className={style.login}>
      <div className={style.card}>
        <label>Id <input type="text" /></label>
      </div>
      <div className={style.card}>
        <label>Password <input type="password" /></label>
      </div>
      <section className={style.btns}>
        <button >login</button>
        <span>no account? click 
          <a href='#' className={style.toSignup}> here!</a>
        </span>
      </section>
    </section>
  )
}