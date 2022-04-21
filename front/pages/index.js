import Head from 'next/head';
import Login from "../components/login"

export default function Home() {
  

  return (
    <>
      <Head>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width" />
        <title>why-we-climb</title>
        {/* <script type="text/javascript" src="../components/main.js"></script> */}
      </Head>

      <main className="intro">
        <section>
          <h2>why we climb</h2>
        </section>
        <Login />
      </main>
      
    </>
  )
}
