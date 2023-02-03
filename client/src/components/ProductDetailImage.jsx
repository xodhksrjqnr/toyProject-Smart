import React from 'react';
import {
  CarouselProvider,
  Slider,
  Slide,
  ButtonBack,
  ButtonNext,
} from 'pure-react-carousel';
import 'pure-react-carousel/dist/react-carousel.es.css';
import {
  IoIosArrowDropleftCircle,
  IoIosArrowDroprightCircle,
} from 'react-icons/io';

export default function ProductDetailImage({ imgFiles, name }) {
  return (
    <div className="w-[32rem] h-[32rem] rounded-2xl overflow-hidden mr-8 shrink-0">
      <CarouselProvider
        naturalSlideWidth={100}
        naturalSlideHeight={100}
        totalSlides={imgFiles.length}
        className="relative"
      >
        <Slider>
          {imgFiles.map((img, index) => (
            <Slide key={index} index={index}>
              <img
                className="w-[32rem] h-[32rem] object-cover"
                src={img}
                alt={name}
              />
            </Slide>
          ))}
        </Slider>
        <div className="absolute top-1/2 flex justify-between w-full text-2xl lg:text-4xl text-white px-2 ">
          <ButtonBack>
            <IoIosArrowDropleftCircle className="bg-slate-700 hover:bg-blue-700 rounded-full" />
          </ButtonBack>
          <ButtonNext>
            <IoIosArrowDroprightCircle className="bg-slate-700 hover:bg-blue-700 rounded-full" />
          </ButtonNext>
        </div>
      </CarouselProvider>
    </div>
  );
}
